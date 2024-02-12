package prus.justweatherapp.feature.locations.mapper

import android.text.SpannableString
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.feature.locations.model.SearchLocationUiModel

fun Location.mapToSearchUiModel(query: String): SearchLocationUiModel =
    SearchLocationUiModel(
        id = this.id,
        name = getSpannableName(this, query)
    )

private fun getSpannableName(location: Location, query: String): SpannableString {
    return SpannableString("${location.city}, ${location.adminName}, ${location.country}")
}
