package prus.justweatherapp.feature.locations

data class LocationsState(
    val searchBarState: SearchBarState,
    val currentScreen: CurrentLocationsScreen
)

data class SearchBarState(
    val searchQuery: String,
    val cancelButtonState: CancelButtonState,
)

sealed interface CancelButtonState {
    data object Hidden : CancelButtonState
    data object Shown : CancelButtonState
}

sealed interface CurrentLocationsScreen {
    data object SearchLocations : CurrentLocationsScreen
    data object UserLocations : CurrentLocationsScreen
}
