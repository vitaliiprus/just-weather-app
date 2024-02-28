package prus.justweatherapp.domain.weather.repository

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.weather.model.CurrentWeather

interface WeatherRepository {

    suspend fun getWeatherByLocationId(locationId: String): Flow<CurrentWeather?>
}