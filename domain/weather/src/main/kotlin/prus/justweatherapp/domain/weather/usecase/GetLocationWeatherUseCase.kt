package prus.justweatherapp.domain.weather.usecase

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.weather.model.CurrentWeather
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import javax.inject.Inject

class GetLocationWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(
        locationId: String
    ): Flow<CurrentWeather?> {
        return weatherRepository.getWeatherByLocationId(locationId)
    }
}