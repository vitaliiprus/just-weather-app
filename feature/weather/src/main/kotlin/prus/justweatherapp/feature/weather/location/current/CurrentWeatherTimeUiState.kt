package prus.justweatherapp.feature.weather.location.current

sealed interface CurrentWeatherTimeUiState {
    data object Loading : CurrentWeatherTimeUiState
    data class Error(val message: String? = null) : CurrentWeatherTimeUiState
    data class Success(val time: String) : CurrentWeatherTimeUiState
}