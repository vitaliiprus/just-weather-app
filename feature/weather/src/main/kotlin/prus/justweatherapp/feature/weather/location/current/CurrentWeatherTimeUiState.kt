package prus.justweatherapp.feature.weather.location.current

import prus.justweatherapp.feature.weather.location.current.daylight.DaylightUiModel

sealed interface CurrentWeatherTimeUiState {
    data object Loading : CurrentWeatherTimeUiState
    data class Error(val message: String? = null) : CurrentWeatherTimeUiState
    data class Success(
        val time: String,
        val daylight:DaylightUiModel
    ) : CurrentWeatherTimeUiState
}