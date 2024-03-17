package prus.justweatherapp.domain.weather.usecase

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import javax.inject.Inject

class GetLocationForecastWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        locationId: String
    ): Flow<RequestResult<List<Weather>>> {
        return weatherRepository.getForecastWeatherByLocationId(locationId)
    }
}