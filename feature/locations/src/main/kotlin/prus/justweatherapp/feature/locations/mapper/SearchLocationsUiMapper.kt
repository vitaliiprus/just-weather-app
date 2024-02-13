package prus.justweatherapp.feature.locations.mapper

import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.feature.locations.model.SearchLocationUiModel

fun Location.mapToSearchUiModel(query: String): SearchLocationUiModel =
    SearchLocationUiModel(
        id = this.id,
        city = this.city,
        adminName = this.adminName,
        country = this.country
    ).apply { findOccurrences(query) }


