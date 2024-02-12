package prus.justweatherapp.feature.locations.search

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import prus.justweatherapp.feature.locations.model.SearchLocationUiModel

@Composable
fun SearchLocationListItem(
    location: SearchLocationUiModel
) {
    Text(
        text = location.name.toString()
    )
}