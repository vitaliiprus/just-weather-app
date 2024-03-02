package prus.justweatherapp.feature.locations.model

import prus.justweatherapp.core.ui.UiText

data class CurrentWeatherUiModel(
    var weatherConditions: UiText,
    var currentTemp: String,
    var minMaxTemp: String,
    var conditionImageResId: Int
)