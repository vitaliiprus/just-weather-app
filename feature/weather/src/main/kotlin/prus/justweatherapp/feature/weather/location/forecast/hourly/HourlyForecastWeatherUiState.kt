package prus.justweatherapp.feature.weather.location.forecast.hourly

sealed interface HourlyForecastWeatherUiState {
    data object Loading : HourlyForecastWeatherUiState
    data class Error(val message: String?) : HourlyForecastWeatherUiState
    data class Success(val weather: List<HourlyForecastWeatherUiModel>) :
        HourlyForecastWeatherUiState
}