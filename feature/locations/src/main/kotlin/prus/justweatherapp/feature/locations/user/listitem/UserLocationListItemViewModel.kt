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
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.core.common.util.CoroutineTimer
import prus.justweatherapp.core.common.util.formatTime
import prus.justweatherapp.core.common.util.getLocationCurrentTime
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.locations.usecase.GetUserLocationByIdUseCase
import prus.justweatherapp.domain.weather.usecase.GetLocationWeatherUseCase
import prus.justweatherapp.feature.locations.mapper.mapToUiModel

@HiltViewModel(assistedFactory = UserLocationListItemViewModel.UserLocationListItemViewModelFactory::class)
class UserLocationListItemViewModel @AssistedInject constructor(
    val getUserLocationByIdUseCase: GetUserLocationByIdUseCase,
    val getLocationWeatherUseCase: GetLocationWeatherUseCase,
    @Assisted val locationId: String
) : ViewModel() {

    @AssistedFactory
    interface UserLocationListItemViewModelFactory {
        fun create(locationId: String): UserLocationListItemViewModel
    }

    private var timezoneOffset: Int? = null
    private var timeUpdater: CoroutineTimer? = null

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
            getLocationWeatherUseCase(locationId)
                .collect { requestResult ->
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
                            val message = requestResult.throwable?.message ?: ""
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
        if (newTimezoneOffset != timezoneOffset) {
            timezoneOffset = newTimezoneOffset
            val initialDelay = (60 - Clock.System.now()
                .toLocalDateTime(TimeZone.currentSystemDefault()).second) * 1000L
            updateTime()
            startTimeUpdater(initialDelay)
        }
    }

    private fun startTimeUpdater(delay: Long) {
        timeUpdater?.cancel()
        timeUpdater = CoroutineTimer(delay) {
            updateTime()
            startTimeUpdater(60 * 1000)
        }
    }

    private fun updateTime() {
        timezoneOffset?.let { timezoneOffset ->
            _state.value = state.value.copy(
                timeState = TimeState.Success(
                    time = UiText.DynamicString(
                        getLocationCurrentTime(timezoneOffset).formatTime()
                    )
                )
            )
        }
    }

    override fun onCleared() {
        timeUpdater?.cancel()
        super.onCleared()
    }
}