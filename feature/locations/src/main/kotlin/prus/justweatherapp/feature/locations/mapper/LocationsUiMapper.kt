package prus.justweatherapp.feature.locations.mapper

import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.feature.locations.model.LocationUiModel

fun Location.mapToUiModel(): LocationUiModel =
    LocationUiModel(
        id = this.id,
        name = this.displayName,
    )

fun List<Location>.mapToUiModels(): List<LocationUiModel> =
    this.map { it.mapToUiModel() }