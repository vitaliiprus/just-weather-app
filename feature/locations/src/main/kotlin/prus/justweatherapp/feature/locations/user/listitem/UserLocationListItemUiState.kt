package prus.justweatherapp.feature.locations.user.listitem

import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.feature.locations.model.CurrentWeatherUiModel
import prus.justweatherapp.feature.locations.model.LocationUiModel

data class UserLocationListItemUiState(
    val locationState: LocationState,
    val timeState: TimeState,
    val weatherState: WeatherState,
)

sealed interface LocationState {
    data object Loading : LocationState
    data class Error(val message: UiText) : LocationState
    data class Success(val location: LocationUiModel) : LocationState
}

sealed interface TimeState {
    data object Loading : TimeState
    data class Error(val message: UiText) : TimeState
    data class Success(val time: UiText) : TimeState
}

sealed interface WeatherState {
    data object Loading : WeatherState
    data class Error(val message: UiText) : WeatherState
    data class Success(val weather: CurrentWeatherUiModel) : WeatherState
}

