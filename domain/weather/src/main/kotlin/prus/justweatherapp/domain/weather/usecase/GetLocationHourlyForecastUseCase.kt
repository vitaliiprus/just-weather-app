package prus.justweatherapp.domain.weather.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.core.common.result.map
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.model.scale.PressureScale
import prus.justweatherapp.domain.weather.model.scale.TempScale
import prus.justweatherapp.domain.weather.model.scale.WindScale
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import prus.justweatherapp.domain.weather.util.getWeatherWithConvertedUnits
import java.util.SortedMap
import javax.inject.Inject

class GetLocationHourlyForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    private val itemsCountNeeded = 24 * 2
    operator fun invoke(
        locationId: String
    ): Flow<RequestResult<SortedMap<LocalDate, List<Weather>>>> {
        return weatherRepository.getForecastWeatherByLocationId(locationId, itemsCountNeeded)
//            .combine(getSettingsUseCase())
            .map { requestResult ->
                val tempScale = TempScale.CELSIUS
                val pressureScale = PressureScale.MM_HG
                val windScale = WindScale.M_S

                val resultData = hashMapOf<LocalDate, List<Weather>>()

                requestResult.map {
                    checkNotNull(requestResult.data)
                        .map { weather ->
                            getWeatherWithConvertedUnits(
                                data = weather,
                                tempScale = tempScale,
                                pressureScale = pressureScale,
                                windScale = windScale
                            )
                        }
                        .forEach { weather ->
                            val key = weather.dateTime.date
                            val list = resultData.getOrDefault(
                                key = key,
                                defaultValue = listOf()
                            )
                            resultData[key] = list.plus(weather)
                        }
                    resultData.toSortedMap()
                }
            }
    }
}