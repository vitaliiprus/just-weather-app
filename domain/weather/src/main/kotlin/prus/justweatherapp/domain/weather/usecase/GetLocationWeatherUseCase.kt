package prus.justweatherapp.domain.weather.usecase

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.domain.weather.model.CurrentWeather
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import javax.inject.Inject

class GetLocationWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        locationId: String
    ): Flow<RequestResult<CurrentWeather?>> {
        return weatherRepository.getCurrentWeatherByLocationId(locationId)
    }
}