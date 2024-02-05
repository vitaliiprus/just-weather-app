package prus.justweatherapp.feature.weather

data class WeatherState(
    val screenState: ScreenState
)

sealed interface ScreenState {
    data object Loading : ScreenState
    data class Error(val message: String) : ScreenState
    data object Success : ScreenState
}