package prus.justweatherapp.feature.locations.user.listitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.core.common.util.TimeUpdater
import prus.justweatherapp.core.common.util.formatTime
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.locations.usecase.GetUserLocationByIdUseCase
import prus.justweatherapp.domain.weather.usecase.GetLocationCurrentWeatherUseCase
import prus.justweatherapp.feature.locations.mapper.mapToUiModel

@HiltViewModel(assistedFactory = UserLocationListItemViewModel.UserLocationListItemViewModelFactory::class)
class UserLocationListItemViewModel @AssistedInject constructor(
    val getUserLocationByIdUseCase: GetUserLocationByIdUseCase,
    val getLocationCurrentWeatherUseCase: GetLocationCurrentWeatherUseCase,
    @Assisted val locationId: String
) : ViewModel() {

    @AssistedFactory
    interface UserLocationListItemViewModelFactory {
        fun create(locationId: String): UserLocationListItemViewModel
    }

    private var timeUpdater: TimeUpdater? = null

    private var _state: MutableStateFlow<UserLocationListItemUiState> =
        MutableStateFlow(
            UserLocationListItemUiState(
                locationState = LocationState.Loading,
                weatherState = WeatherState.Loading,
                timeState = TimeState.Loading,
            )
        )

    var state: StateFlow<UserLocationListItemUiState> = _state

    init {
        getLocation(locationId)
        getLocationWeather(locationId)
    }

    private fun getLocation(locationId: String) {
        viewModelScope.launch {
            getUserLocationByIdUseCase(locationId)?.let { location ->
                _state.value = state.value.copy(
                    locationState = LocationState.Success(location.mapToUiModel())
                )
            }
        }
    }

    private fun getLocationWeather(locationId: String) {
        viewModelScope.launch {
            getLocationCurrentWeatherUseCase(locationId)
                .collect { requestResult ->
                    timeUpdater?.cancel()
                    _state.value = when (requestResult) {
                        is RequestResult.Success -> {
                            val data = checkNotNull(requestResult.data)
                            onNewTimeZoneOffset(data.timezoneOffset)
                            state.value.copy(
                                weatherState = WeatherState.Success(
                                    weather = data.mapToUiModel()
                                )
                            )
                        }

                        is RequestResult.Loading -> {
                            state.value.copy(
                                weatherState = WeatherState.Loading
                            )
                        }

                        is RequestResult.Error -> {
                            val message = requestResult.error?.message ?: ""
                            state.value.copy(
                                weatherState = WeatherState.Error(
                                    message = UiText.DynamicString(message)
                                )
                            )
                        }
                    }
                }
        }
    }

    private fun onNewTimeZoneOffset(newTimezoneOffset: Int) {
        timeUpdater = TimeUpdater(newTimezoneOffset) { newTime ->
            _state.value = state.value.copy(
                timeState = TimeState.Success(
                    time = UiText.DynamicString(newTime.formatTime())
                )
            )
        }
    }

    override fun onCleared() {
        timeUpdater?.cancel()
        super.onCleared()
    }
}