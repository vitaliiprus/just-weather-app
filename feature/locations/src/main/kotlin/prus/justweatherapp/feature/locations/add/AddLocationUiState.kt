package prus.justweatherapp.feature.locations.add

import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.feature.locations.model.LocationUiModel

data class AddLocationUiState(
    val locationDataState: LocationDataState,
    val weatherDataState: WeatherDataState,
    var isLocationAdded: Boolean = false
)

sealed interface LocationDataState {
    data object Loading : LocationDataState
    data class Error(val message: UiText) : LocationDataState
    data class Success(val location: LocationUiModel) : LocationDataState
}

sealed interface WeatherDataState {
    data object Loading : WeatherDataState
    data object Error : WeatherDataState
    data class Success(val weather: String) : WeatherDataState
}
