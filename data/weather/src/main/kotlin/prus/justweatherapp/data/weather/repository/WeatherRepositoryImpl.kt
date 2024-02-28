package prus.justweatherapp.data.weather.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import prus.justweatherapp.data.weather.mapper.mapToDomainModel
import prus.justweatherapp.domain.weather.model.CurrentWeather
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import prus.justweatherapp.local.db.dao.LocationsDao
import prus.justweatherapp.remote.datasource.WeatherDataSource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val locationsDao: LocationsDao
) : WeatherRepository {

    override suspend fun getWeatherByLocationId(locationId: String): Flow<CurrentWeather?> = flow {
        locationsDao.getLocationById(locationId)?.let {
            emit(weatherDataSource.getWeatherData(it.lat, it.lng).mapToDomainModel(it.locationId))
        } ?: emit(null)
    }
}