package prus.justweatherapp.feature.locations.mapper

import prus.justweatherapp.core.common.util.isBetween
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.feature.locations.model.CurrentWeatherUiModel
import prus.justweatherapp.feature.weather.mapper.getTempString
import prus.justweatherapp.feature.weather.mapper.getWeatherConditionImageResId
import prus.justweatherapp.feature.weather.mapper.getWeatherConditionsString
import kotlin.math.roundToInt

fun Weather.mapToUiModel(): CurrentWeatherUiModel =
    CurrentWeatherUiModel(
        currentTemp = getCurrentTempString(this.temp),
        minMaxTemp = getMinMaxTempString(this.tempMin, this.tempMax),
        weatherConditions = getWeatherConditionsString(this.weatherConditions),
        conditionImageResId = getWeatherConditionImageResId(
            weatherConditions = this.weatherConditions,
            isDay = this.dateTime.time.isBetween(this.sunrise, this.sunset)
        )
    )

private fun getCurrentTempString(currentTemp: Double): String {
    return "${currentTemp.roundToInt()}º"
}

private fun getMinMaxTempString(
    minTemp: Double?,
    maxTemp: Double?
): String {
    val tempMinString = minTemp?.let {
        getTempString(minTemp, false)
    } ?: "-"
    val tempMaxString = maxTemp?.let {
        getTempString(maxTemp, false)
    } ?: "-"
    return "↓${tempMinString} ↑${tempMaxString}"
}