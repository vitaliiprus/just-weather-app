package prus.justweatherapp.feature.weather.location.current

import prus.justweatherapp.core.ui.UiText

data class CurrentWeatherUiModel(
    val temp:String,
    val feelsLike: UiText,
    var weatherConditions: UiText,
    var conditionImageResId: Int,
    val sunrise: String,
    val sunset: String,
    val tempMinMax:String,
    val uvIndex:Int,
    val pressure: UiText,
    val precipitationProb:String,
    val humidity:String,
    val wind: UiText,
)