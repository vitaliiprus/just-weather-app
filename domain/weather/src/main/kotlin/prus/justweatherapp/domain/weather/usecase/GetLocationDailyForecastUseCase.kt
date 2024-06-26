package prus.justweatherapp.domain.weather.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.datetime.LocalDate
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.domain.settings.usecase.GetSettingsUseCase
import prus.justweatherapp.domain.weather.mapper.toDomainModel
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.model.WeatherConditions
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import prus.justweatherapp.domain.weather.util.getWeatherWithConvertedUnits
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class GetLocationDailyForecastUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val getSettingsUseCase: GetSettingsUseCase
) {
    private val itemsCountNeeded = 24 * 11
    private val days = 10

    operator fun invoke(
        locationId: String
    ): Flow<RequestResult<List<Weather>>> {
        return weatherRepository.getForecastWeatherByLocationId(locationId, itemsCountNeeded)
            .combine(
                getSettingsUseCase()
            ) { requestResult, settingsModel ->
                val tempScale = settingsModel.tempScale.toDomainModel()
                val pressureScale = settingsModel.pressureScale.toDomainModel()
                val windScale = settingsModel.windScale.toDomainModel()

                val dataByDay = hashMapOf<LocalDate, List<Weather>>()

                if (requestResult is RequestResult.Success) {
                    checkNotNull(requestResult.data)
                        .forEach { weather ->
                            val key = weather.dateTime.date
                            val list = dataByDay.getOrDefault(
                                key = key,
                                defaultValue = listOf()
                            )
                            dataByDay[key] = list.plus(weather)
                        }

                    val result = dataByDay
                        .map { (_, list) ->
                            foldHourlyDataIntoDay(list)
                        }.sortedBy {
                            it.dateTime
                        }
                        .take(days)

                    RequestResult.Success(
                        result.map { weather ->
                            getWeatherWithConvertedUnits(
                                data = weather,
                                tempScale = tempScale,
                                pressureScale = pressureScale,
                                windScale = windScale
                            )
                        }
                    )
                } else {
                    requestResult
                }
            }
    }

    private fun foldHourlyDataIntoDay(list: List<Weather>): Weather {
        return list.fold(
            initial = list.first().copy(
                tempMin = list.first().temp,
                tempMax = list.first().temp,
            )
        ) { daily, hourly ->
            daily.copy(
                feelsLike = min(
                    daily.feelsLike,
                    hourly.feelsLike
                ),
                tempMin = min(
                    daily.tempMin!!,
                    hourly.temp
                ),
                tempMax = max(
                    daily.tempMax!!,
                    hourly.temp
                ),
                pressure = max(
                    daily.pressure,
                    hourly.pressure
                ),
                humidity = max(
                    daily.humidity,
                    hourly.humidity
                ),
                weatherConditions = WeatherConditions.fromValue(
                    max(
                        daily.weatherConditions.code,
                        hourly.weatherConditions.code
                    )
                ),
                clouds = max(
                    daily.clouds ?: 0.0,
                    hourly.clouds ?: 0.0
                ),
                rain = max(
                    daily.rain ?: 0.0,
                    hourly.rain ?: 0.0
                ),
                snow = max(
                    daily.snow ?: 0.0,
                    hourly.snow ?: 0.0
                ),
                wind = if ((daily.wind?.speed ?: 0.0) > (hourly.wind?.speed ?: 0.0))
                    daily.wind
                else
                    hourly.wind,
                visibility = min(
                    daily.visibility ?: 0,
                    hourly.visibility ?: 0
                ),
                uvi = max(
                    daily.uvi ?: 0.0,
                    hourly.uvi ?: 0.0
                ),
                probOfPrecipitations = max(
                    daily.probOfPrecipitations ?: 0.0,
                    hourly.probOfPrecipitations ?: 0.0
                ),
            )
        }
    }
}