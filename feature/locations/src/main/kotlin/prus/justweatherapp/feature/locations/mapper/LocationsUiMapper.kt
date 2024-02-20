package prus.justweatherapp.feature.locations.mapper

import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.feature.locations.model.LocationUiModel

fun Location.mapToUiModel(): LocationUiModel =
    LocationUiModel(
        id = this.id,
        name = this.displayName ?: this.city,
        //TODO: stub for now
        time = "12:05",
        weatherConditions = UiText.DynamicString("Partially cloudy"),
        currentTemp = "-2º",
        minMaxTemp = "↓-10º ↑4º",
        conditionImageResId = prus.justweatherapp.core.ui.R.drawable.mostlysunny
    )

fun List<Location>.mapToUiModels(): List<LocationUiModel> =
    this.map { it.mapToUiModel() }