package prus.justweatherapp.data.weather.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.core.common.result.map
import prus.justweatherapp.core.common.result.toRequestResult
import prus.justweatherapp.data.weather.mapper.mapToCurrentWeatherDomainModel
import prus.justweatherapp.data.weather.mapper.mapToDBO
import prus.justweatherapp.domain.weather.model.CurrentWeather
import prus.justweatherapp.domain.weather.model.ForecastWeather
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import prus.justweatherapp.local.db.dao.LocationsDao
import prus.justweatherapp.local.db.dao.WeatherDao
import prus.justweatherapp.local.db.entity.WeatherEntity
import prus.justweatherapp.remote.datasource.WeatherDataSource
import prus.justweatherapp.remote.model.CurrentWeatherDTO
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val locationsDao: LocationsDao,
    private val weatherDao: WeatherDao,
) : WeatherRepository {

    override suspend fun getCurrentWeatherByLocationId(
        locationId: String
    ): Flow<RequestResult<CurrentWeather?>> = flow<RequestResult<CurrentWeather?>> {
        getCurrentWeatherFromDb(locationId)
            .collect { dbRequestResult ->
                when (dbRequestResult) {
                    is RequestResult.Success -> {
                        emit(dbRequestResult.map { it?.mapToCurrentWeatherDomainModel() })
                    }

                    is RequestResult.Error -> {
                        emitAll(getCurrentWeatherFromServer(locationId))
                    }

                    is RequestResult.Loading -> {
                        emit(RequestResult.Loading())
                    }
                }
            }
    }.onStart { emit(RequestResult.Loading()) }

    private fun getCurrentWeatherFromDb(
        locationId: String
    ): Flow<RequestResult<WeatherEntity?>> = flow {
        val weatherEntity = weatherDao.getCurrentWeatherByLocationId(
            locationId = locationId,
            dateFrom = Clock.System.now()
                .plus(-30, DateTimeUnit.MINUTE)
                .toLocalDateTime(TimeZone.currentSystemDefault())
        )
        if (weatherEntity == null) {
            emit(
                RequestResult.Error(
                    throwable = Throwable("No current weather data in the database")
                )
            )
            return@flow
        }

        emit(RequestResult.Success(weatherEntity))
    }.onStart { emit(RequestResult.Loading()) }

    private suspend fun getCurrentWeatherFromServer(
        locationId: String
    ): Flow<RequestResult<CurrentWeather?>> = flow {
        val location = locationsDao.getLocationById(
            locationId = locationId
        )
        if (location == null) {
            emit(
                RequestResult.Error(
                    throwable = Throwable("Cannot find the location in the database")
                )
            )
            return@flow
        }

        weatherDataSource.getCurrentWeatherData(location.lat, location.lng)
            .toRequestResult()
            .also { apiRequestResult ->
                if (apiRequestResult is RequestResult.Success) {
                    saveApiResponseToDb(checkNotNull(apiRequestResult.data), locationId)
                }
                emit(apiRequestResult.map { it.mapToCurrentWeatherDomainModel(locationId) })
            }
    }.onStart { emit(RequestResult.Loading()) }

    private suspend fun saveApiResponseToDb(response: CurrentWeatherDTO, locationId: String) {
        weatherDao.insertAll(listOf(response.mapToDBO(locationId)))
    }

    override suspend fun getForecastWeatherByLocationId(locationId: String)
            : Flow<List<ForecastWeather?>> = flow {

    }
}