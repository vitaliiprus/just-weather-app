package prus.justweatherapp.domain.weather.repository

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.domain.weather.model.Weather

interface WeatherRepository {

    suspend fun getCurrentWeatherByLocationId(locationId: String): Flow<RequestResult<Weather?>>

    suspend fun getForecastWeatherByLocationId(locationId: String): Flow<RequestResult<List<Weather>>>
}