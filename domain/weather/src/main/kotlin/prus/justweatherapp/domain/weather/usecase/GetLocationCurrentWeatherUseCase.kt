package prus.justweatherapp.domain.weather.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.domain.settings.usecase.GetSettingsUseCase
import prus.justweatherapp.domain.weather.mapper.toDomainModel
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.repository.WeatherRepository
import prus.justweatherapp.domain.weather.util.getWeatherWithConvertedUnits
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

class GetLocationCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val getSettingsUseCase: GetSettingsUseCase
) {
    private val itemsCountNeeded = 24

    operator fun invoke(
        locationId: String
    ): Flow<RequestResult<Weather?>> {
        return weatherRepository.getCurrentWeatherByLocationId(
            locationId = locationId
        )
            .combine(
                weatherRepository.getForecastWeatherByLocationId(
                    locationId = locationId,
                    count = itemsCountNeeded
                )
            ) { currentResult, forecastResult ->
                if (currentResult is RequestResult.Success && forecastResult is RequestResult.Success) {
                    RequestResult.Success(
                        getWeatherWithCalculatedMinMaxTemp(
                            currentData = checkNotNull(currentResult.data),
                            forecastData = checkNotNull(forecastResult.data),
                        )
                    )
                } else {
                    currentResult
                }
            }
            .combine(
                getSettingsUseCase()
            ) { requestResult, settingsModel ->
                val tempScale = settingsModel.tempScale.toDomainModel()
                val pressureScale = settingsModel.pressureScale.toDomainModel()
                val windScale = settingsModel.windScale.toDomainModel()

                if (requestResult is RequestResult.Success) {
                    RequestResult.Success(
                        getWeatherWithConvertedUnits(
                            data = checkNotNull(requestResult.data),
                            tempScale = tempScale,
                            pressureScale = pressureScale,
                            windScale = windScale
                        )
                    )
                } else {
                    requestResult
                }
            }
    }

    private fun getWeatherWithCalculatedMinMaxTemp(
        currentData: Weather,
        forecastData: List<Weather>
    ): Weather {
        var minTemp = currentData.temp
        var maxTemp = currentData.temp

        forecastData
            .filter { it.dateTime.date == currentData.dateTime.date }
            .forEach {
                minTemp = min(minTemp, it.temp)
                maxTemp = max(maxTemp, it.temp)
            }

        return currentData.copy(
            tempMin = minTemp,
            tempMax = maxTemp
        )
    }
}