package prus.justweatherapp.feature.weather.location.current

import prus.justweatherapp.core.ui.UiText

data class CurrentWeatherUiModel(
    val dateTime:String,
    val temp:String,
    val feelsLike:String,
    val tempMinMax:String,
    var weatherConditions: UiText,
    var conditionImageResId: Int,
    val sunrise: String,
    val daylight: String,
    val sunset: String,
)