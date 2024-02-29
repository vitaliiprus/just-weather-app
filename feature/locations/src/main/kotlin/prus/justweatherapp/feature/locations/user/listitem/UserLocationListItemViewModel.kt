package prus.justweatherapp.feature.locations.user.listitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.domain.weather.usecase.GetLocationWeatherUseCase
import prus.justweatherapp.feature.locations.mapper.mapToUiModel
import prus.justweatherapp.feature.locations.model.LocationUiModel
import javax.inject.Inject

@HiltViewModel
class UserLocationListItemViewModel @Inject constructor(
    val getLocationWeatherUseCase: GetLocationWeatherUseCase,
) : ViewModel() {

    private var _state: MutableStateFlow<UserLocationListItemUiState> =
        MutableStateFlow(
            UserLocationListItemUiState(
                locationState = LocationState.Loading,
                weatherState = WeatherState.Loading,
                isEditing = false
            )
        )

    var state: StateFlow<UserLocationListItemUiState> = _state

    fun setLocation(location: LocationUiModel) {
        _state.update { state ->
            state.copy(
                locationState = LocationState.Success(location)
            )
        }
        getLocationWeather(location.id)
    }

    private fun getLocationWeather(locationId: String) {
        viewModelScope.launch {
            getLocationWeatherUseCase(locationId)
                .asResult()
                .collect { result ->
                    result.getOrNull()?.let { weather ->
                        _state.update { state ->
                            state.copy(
                                weatherState = WeatherState.Success(weather.mapToUiModel())
                            )
                        }
                    }
                }
        }
    }

    fun setIsEditing(isEditing: Boolean) {
        _state.update { state ->
            state.copy(
                isEditing = isEditing
            )
        }
    }
}