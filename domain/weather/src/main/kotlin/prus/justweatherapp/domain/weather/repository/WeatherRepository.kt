package prus.justweatherapp.domain.weather.repository

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.domain.weather.model.CurrentWeather
import prus.justweatherapp.domain.weather.model.ForecastWeather

interface WeatherRepository {

    suspend fun getCurrentWeatherByLocationId(locationId: String): Flow<RequestResult<CurrentWeather?>>

    suspend fun getForecastWeatherByLocationId(locationId: String): Flow<List<ForecastWeather?>>
}