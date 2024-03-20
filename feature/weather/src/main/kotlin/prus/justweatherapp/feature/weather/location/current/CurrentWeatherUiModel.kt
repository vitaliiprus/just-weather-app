package prus.justweatherapp.feature.weather.location.current

import prus.justweatherapp.core.ui.UiText

data class CurrentWeatherUiModel(
    val dateTime:String,
    val temp:String,
    val feelsLike: UiText,
    var weatherConditions: UiText,
    var conditionImageResId: Int,
    val sunrise: String,
    val daylight: String,
    val sunset: String,
    val tempMinMax:String,
    val uvIndex:String,
    val pressure: UiText,
    val precipitationProb:String,
    val humidity:String,
    val wind: UiText,
)