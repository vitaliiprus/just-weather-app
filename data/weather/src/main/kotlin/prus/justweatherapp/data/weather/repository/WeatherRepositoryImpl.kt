package prus.justweatherapp.data.weather.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
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
import prus.justweatherapp.data.weather.mergestrategy.ForecastWeatherMergeStrategy
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import prus.justweatherapp.local.db.dao.LocationsDao
import prus.justweatherapp.local.db.dao.WeatherDao
import prus.justweatherapp.local.db.entity.WeatherEntity
import prus.justweatherapp.remote.datasource.WeatherDataSource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val locationsDao: LocationsDao,
    private val weatherDao: WeatherDao,
) : WeatherRepository {

    private val currentWeatherDataRefreshTimeMinutes = 30
    private val forecastWeatherListMaxSize = 40

    override fun getCurrentWeatherByLocationId(
        locationId: String
    ): Flow<RequestResult<Weather?>> = flow {
        getCurrentWeatherFromDb(locationId)
            .collect { dbRequestResult ->
                when (dbRequestResult) {
                    is RequestResult.Error -> {
                        emitAll(getCurrentWeatherFromServer(locationId))
                    }

                    else -> {
                        emit(dbRequestResult)
                    }
                }
            }
    }.onStart { emit(RequestResult.Loading()) }

    private fun getCurrentWeatherFromDb(
        locationId: String
    ): Flow<RequestResult<Weather?>> = flow {
        val weatherEntity = weatherDao.getCurrentWeatherByLocationId(
            locationId = locationId,
            dateFrom = Clock.System.now()
                .plus(-currentWeatherDataRefreshTimeMinutes, DateTimeUnit.MINUTE)
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )
        if (weatherEntity == null) {
            emit(
                RequestResult.Error(
                    error = Throwable("No current weather data in the database")
                )
            )
            return@flow
        }

        emit(RequestResult.Success(weatherEntity.mapToDomainModel()))
    }.onStart { emit(RequestResult.Loading()) }

    private suspend fun getCurrentWeatherFromServer(
        locationId: String
    ): Flow<RequestResult<Weather?>> = flow {
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

        weatherDataSource.getCurrentWeatherData(location.lat, location.lng)
            .toRequestResult()
            .also { apiRequestResult ->
                if (apiRequestResult is RequestResult.Success) {
                    saveApiResponseToDb(
                        response = listOf(checkNotNull(apiRequestResult.data).mapToDBO(locationId))
                    )
                }
                emit(apiRequestResult.map { it.mapToDomainModel(locationId) })
            }
    }.onStart { emit(RequestResult.Loading()) }

    private suspend fun saveApiResponseToDb(response: List<WeatherEntity>) {
        weatherDao.insertAll(response)
    }

    override fun getForecastWeatherByLocationId(
        locationId: String,
    ): Flow<RequestResult<List<Weather>>> = flow {
        val mergeStrategy = ForecastWeatherMergeStrategy<List<Weather>>()

        getForecastWeatherFromDb(locationId)
            .collect { dbRequestResult ->
                if (dbRequestResult is RequestResult.Success
                    && checkNotNull(dbRequestResult.data).size < forecastWeatherListMaxSize
                ) {
                    emitAll(
                        getForecastWeatherFromServer(locationId)
                            .combine(
                                flowOf(dbRequestResult),
                                mergeStrategy::merge
                            )
                    )
                } else {
                    emit(dbRequestResult)
                }
            }
    }

    private fun getForecastWeatherFromDb(
        locationId: String
    ): Flow<RequestResult<List<Weather>>> = flow {
        emit(
            weatherDao.getForecastWeatherByLocationId(
                locationId = locationId,
                limit = forecastWeatherListMaxSize
            ).map { it.mapToDomainModel() }
        )
    }
        .map { RequestResult.Success(it) }
        .onStart { RequestResult.Loading(data = null) }

    private suspend fun getForecastWeatherFromServer(
        locationId: String
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
                when (apiRequestResult) {
                    is RequestResult.Success -> {
                        val data = checkNotNull(apiRequestResult.data)
                        val dbos = data.list.map { it.mapToDBO(locationId, data.city) }
                        saveApiResponseToDb(
                            response = dbos
                        )
                        emit(RequestResult.Success(dbos.map { it.mapToDomainModel() }))
                    }

                    is RequestResult.Loading ->
                        emit(RequestResult.Loading(null))

                    is RequestResult.Error ->
                        emit(RequestResult.Error(data = null, error = apiRequestResult.error))
                }

            }
    }.onStart { emit(RequestResult.Loading(null)) }
}