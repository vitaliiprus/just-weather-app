package prus.justweatherapp.feature.home

data class HomeState(
    val screenState: ScreenState
)

sealed interface ScreenState {
    object Loading : ScreenState
    data class Error(val message: String) : ScreenState
    data object Success : ScreenState
}