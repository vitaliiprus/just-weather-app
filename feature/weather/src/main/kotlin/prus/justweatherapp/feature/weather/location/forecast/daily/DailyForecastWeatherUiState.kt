package prus.justweatherapp.feature.weather.location.forecast.daily

sealed interface DailyForecastWeatherUiState {
    data object Loading : DailyForecastWeatherUiState
    data class Error(val message: String?) : DailyForecastWeatherUiState
    data class Success(val weather: List<DailyForecastWeatherUiModel>) : DailyForecastWeatherUiState
}