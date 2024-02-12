package prus.justweatherapp.feature.locations

data class LocationsState(
    val currentScreen: CurrentLocationsScreen
)

sealed interface CurrentLocationsScreen {
    data object SearchLocations : CurrentLocationsScreen
    data object UserLocations : CurrentLocationsScreen
}
