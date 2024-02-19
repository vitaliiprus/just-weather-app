package prus.justweatherapp.feature.locations.model

import prus.justweatherapp.core.ui.UiText

data class LocationUiModel(
    val name: String,
    val time: String,
    val weatherConditions: UiText,
    val currentTemp: String,
    val minMaxTemp: String,
    val conditionImageResId: Int
)