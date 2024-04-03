package prus.justweatherapp.domain.weather.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.model.scale.PressureScale
import prus.justweatherapp.domain.weather.model.scale.TempScale
import prus.justweatherapp.domain.weather.model.scale.WindScale
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import prus.justweatherapp.domain.weather.util.convertPressure
import prus.justweatherapp.domain.weather.util.convertTemp
import prus.justweatherapp.domain.weather.util.convertWind
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
                val pressureScale = PressureScale.MM_HG
                val windScale = WindScale.M_S
                if (requestResult is RequestResult.Success) {
                    val data = checkNotNull(requestResult.data)
                    RequestResult.Success(
                        data.copy(
                            temp = convertTemp(data.temp, tempScale),
                            feelsLike = convertTemp(data.feelsLike, tempScale),
                            tempMin = if (data.tempMin != null)
                                convertTemp(data.tempMin, tempScale) else null,
                            tempMax = if (data.tempMax != null)
                                convertTemp(data.tempMax, tempScale) else null,
                            tempScale = tempScale,
                            pressure = convertPressure(data.pressure, pressureScale),
                            pressureScale = pressureScale,
                            wind = data.wind?.copy(
                                speed = data.wind.speed?.let {
                                    convertWind(it, windScale)
                                },
                                gust = data.wind.gust?.let {
                                    convertWind(it, windScale)
                                },
                                windScale = windScale
                            )
                        )
                    )
                } else {
                    requestResult
                }
            }
    }
}