package prus.justweatherapp.feature.settings

sealed interface SettingsUiState {
    data object Loading : SettingsUiState
    data class Error(val message: String) : SettingsUiState
    data class Success(
        val settings: SettingsUiModel
    ) : SettingsUiState
}