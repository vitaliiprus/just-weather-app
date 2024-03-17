package prus.justweatherapp.feature.weather.location.current

sealed interface CurrentWeatherUiState {
    data object Loading : CurrentWeatherUiState
    data class Error(val message: String?) : CurrentWeatherUiState
    data class Success(val weather: CurrentWeatherUiModel) : CurrentWeatherUiState
}