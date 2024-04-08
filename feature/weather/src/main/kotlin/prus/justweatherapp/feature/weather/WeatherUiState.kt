package prus.justweatherapp.feature.weather

sealed interface WeatherUiState {
    data object Loading : WeatherUiState
    data class Error(val message: String) : WeatherUiState
    data object Empty : WeatherUiState
    data class Success(
        val locationIdsNames: List<Pair<String, String>>,
        val initialPage:Int
    ) : WeatherUiState
}