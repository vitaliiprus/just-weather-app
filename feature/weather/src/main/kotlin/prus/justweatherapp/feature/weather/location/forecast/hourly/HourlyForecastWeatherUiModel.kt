package prus.justweatherapp.feature.weather.location.forecast.hourly

import prus.justweatherapp.core.ui.UiText

data class HourlyForecastWeatherUiModel(
    val conditionImageResId: Int,
    var weatherConditions: UiText,
    val time: String,
    val temp: String,
    val precipitationProb: String? = null,
    val date: String? = null,
)
