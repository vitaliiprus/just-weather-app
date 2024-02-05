package prus.justweatherapp.feature.settings

data class SettingsState(
    val screenState: ScreenState
)

sealed interface ScreenState {
    data object Loading : ScreenState
    data class Error(val message: String) : ScreenState
    data object Success : ScreenState
}