package prus.justweatherapp.app

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val data: MainActivityUiModel) : MainActivityUiState
}