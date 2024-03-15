package prus.justweatherapp.domain.weather.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.domain.weather.model.TempScale
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import prus.justweatherapp.domain.weather.util.convertTemp
import javax.inject.Inject

class GetLocationCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        locationId: String
    ): Flow<RequestResult<Weather?>> {
        return weatherRepository.getCurrentWeatherByLocationId(locationId)
//            .combine(getSettingsUseCase())
            .map { requestResult ->
                val tempScale = TempScale.CELSIUS
                if (requestResult is RequestResult.Success) {
                    val data = checkNotNull(requestResult.data)
                    RequestResult.Success(
                        checkNotNull(requestResult.data).copy(
                            temp = convertTemp(data.temp, tempScale),
                            feelsLike = convertTemp(data.feelsLike, tempScale),
                            tempMin = convertTemp(data.tempMin, tempScale),
                            tempMax = convertTemp(data.tempMax, tempScale),
                            tempScale = tempScale
                        )
                    )
                } else {
                    requestResult
                }
            }
    }
}