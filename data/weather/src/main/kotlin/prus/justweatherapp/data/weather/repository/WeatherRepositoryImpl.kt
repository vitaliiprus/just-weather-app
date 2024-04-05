package prus.justweatherapp.data.weather.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.core.common.result.map
import prus.justweatherapp.core.common.result.toRequestResult
import prus.justweatherapp.data.weather.mapper.mapToDBO
import prus.justweatherapp.data.weather.mapper.mapToDomainModel
import prus.justweatherapp.data.weather.mapper.mapToDomainModels
import prus.justweatherapp.data.weather.mergestrategy.RequestResultMergeStrategy
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import prus.justweatherapp.local.db.dao.LocationsDao
import prus.justweatherapp.local.db.dao.SunDataDao
import prus.justweatherapp.local.db.dao.WeatherDao
import prus.justweatherapp.remote.datasource.WeatherDataSource
import prus.justweatherapp.remote.model.ForecastResponseDTO
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val locationsDao: LocationsDao,
    private val weatherDao: WeatherDao,
    private val sunDataDao: SunDataDao,
) : WeatherRepository {

    private val currentWeatherDataRefreshTimeMinutes = 30
    private val forecastDaysMaxCount = 14

    override fun getCurrentWeatherByLocationId(
        locationId: String
    ): Flow<RequestResult<Weather?>> = flow {
        val mergeStrategy = RequestResultMergeStrategy<Weather?>()

        getCurrentWeatherFromDb(locationId)
            .onEach { dbRequestResult ->
                when (dbRequestResult) {
                    is RequestResult.Error -> {
                        emitAll(
                            flowOf(dbRequestResult)
                                .combine(
                                    getForecastWeatherFromServer(
                                        locationId = locationId,
                                        count = 1
                                    ).map { result -> result.map { it.firstOrNull() } },
                                    mergeStrategy::merge
                                )
                        )
                    }

                    else -> {
                        emit(dbRequestResult)
                    }
                }
            }.collect()
    }

    private fun getCurrentWeatherFromDb(
        locationId: String
    ): Flow<RequestResult<Weather?>> = flow {
        val weatherEntity = weatherDao.getCurrentWeatherByLocationId(
            locationId = locationId,
            dateFrom = Clock.System.now()
                .plus(-currentWeatherDataRefreshTimeMinutes, DateTimeUnit.MINUTE)
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )
        val sunDataEntity = sunDataDao.getDataByLocationId(
            locationId = locationId,
            dateFrom = Clock.System.now()
                .plus(-currentWeatherDataRefreshTimeMinutes, DateTimeUnit.MINUTE)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date,
            limit = 1
        ).firstOrNull()

        if (weatherEntity == null || sunDataEntity == null) {
            emit(
                RequestResult.Error(
                    error = Throwable("No current weather data in the database")
                )
            )
            return@flow
        }

        emit(RequestResult.Success(Pair(weatherEntity, sunDataEntity).mapToDomainModel()))
    }.onStart { emit(RequestResult.Loading()) }


    override fun getForecastWeatherByLocationId(
        locationId: String,
        count: Int
    ): Flow<RequestResult<List<Weather>>> = flow {
        val mergeStrategy = RequestResultMergeStrategy<List<Weather>>()

        getForecastWeatherFromDb(locationId, count)
            .onEach { dbRequestResult ->
                if (dbRequestResult is RequestResult.Success &&
                    checkNotNull(dbRequestResult.data).size < count
                ) {
                    emitAll(
                        flowOf(dbRequestResult)
                            .combine(
                                getForecastWeatherFromServer(
                                    locationId = locationId,
                                    count = count
                                ),
                                mergeStrategy::merge
                            )
                    )
                } else {
                    emit(dbRequestResult)
                }
            }.collect()
    }

    private fun getForecastWeatherFromDb(
        locationId: String,
        count: Int
    ): Flow<RequestResult<List<Weather>>> = flow {
        val weatherDbos = weatherDao.getForecastWeatherByLocationId(
            locationId = locationId,
            limit = count
        )
        val sunDataDbos = sunDataDao.getDataByLocationId(
            locationId = locationId,
            dateFrom = Clock.System.now()
                .plus(-currentWeatherDataRefreshTimeMinutes, DateTimeUnit.MINUTE)
                .toLocalDateTime(TimeZone.currentSystemDefault())
                .date,
            limit = forecastDaysMaxCount
        )

        emit(RequestResult.Success(
            weatherDbos
                .filter { weatherDbo ->
                    sunDataDbos.firstOrNull { it.date == weatherDbo.dateTime.date } != null
                }
                .map { weatherDbo ->
                    val sunDataDbo = sunDataDbos.first { it.date == weatherDbo.dateTime.date }
                    Pair(weatherDbo, sunDataDbo).mapToDomainModel()
                }
        ))
    }.onStart { RequestResult.Loading(data = null) }

    private suspend fun getForecastWeatherFromServer(
        locationId: String,
        count: Int
    ): Flow<RequestResult<List<Weather>>> = flow {
        val location = locationsDao.getLocationById(
            locationId = locationId
        )
        if (location == null) {
            emit(
                RequestResult.Error(
                    error = Throwable("Cannot find the location in the database")
                )
            )
            return@flow
        }

        weatherDataSource.getForecastWeatherData(location.lat, location.lng)
            .toRequestResult()
            .also { apiRequestResult ->
                if (apiRequestResult is RequestResult.Success) {
                    saveServerResponseToDb(checkNotNull(apiRequestResult.data), locationId)
                }
                emit(
                    apiRequestResult
                        .map {
                            it.mapToDomainModels(locationId)
                        }
                        .map { data ->
                            data.sortedBy {
                                it.dateTime
                            }
                        }
                        .map {
                            it.take(count)
                        }
                )
            }
    }.onStart { emit(RequestResult.Loading()) }

    private suspend fun saveServerResponseToDb(response: ForecastResponseDTO, locationId: String) {
        weatherDao.insertAll(response.hourly.map { it.mapToDBO(locationId) })
        sunDataDao.insertAll(response.sun.map { it.mapToDBO(locationId, response.timezoneOffset) })
    }
}