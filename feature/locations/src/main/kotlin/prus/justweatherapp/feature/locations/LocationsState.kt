package prus.justweatherapp.feature.locations

data class LocationsState(
    val screenState: ScreenState
)

sealed interface ScreenState {
    data object Loading : ScreenState
    data class Error(val message: String) : ScreenState
    data object Success : ScreenState
}