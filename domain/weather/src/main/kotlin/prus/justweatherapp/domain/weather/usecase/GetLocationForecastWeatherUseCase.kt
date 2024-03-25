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

class GetLocationForecastWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    operator fun invoke(
        locationId: String
    ): Flow<RequestResult<List<Weather>>> {
        return weatherRepository.getForecastWeatherByLocationId(locationId)
//            .combine(getSettingsUseCase())
            .map { requestResult ->
                val tempScale = TempScale.CELSIUS
                val pressureScale = PressureScale.MM_HG
                val windScale = WindScale.M_S
                if (requestResult is RequestResult.Success) {
                    val data = checkNotNull(requestResult.data)
                    RequestResult.Success(
                        data.map { weather ->
                            weather.copy(
                                temp = convertTemp(weather.temp, tempScale),
                                feelsLike = convertTemp(weather.feelsLike, tempScale),
                                tempMin = convertTemp(weather.tempMin, tempScale),
                                tempMax = convertTemp(weather.tempMax, tempScale),
                                tempScale = tempScale,
                                pressure = convertPressure(weather.pressure, pressureScale),
                                pressureScale = pressureScale,
                                wind = weather.wind?.copy(
                                    speed = weather.wind.speed?.let {
                                        convertWind(it, windScale)
                                    },
                                    gust = weather.wind.gust?.let {
                                        convertWind(it, windScale)
                                    },
                                    windScale = windScale
                                )
                            )
                        }
                    )
                } else {
                    requestResult
                }
            }
    }
}