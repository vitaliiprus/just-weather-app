package prus.justweatherapp.feature.locations.user.listitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.core.common.util.CoroutineTimer
import prus.justweatherapp.core.common.util.formatTime
import prus.justweatherapp.core.common.util.getLocationCurrentTime
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.weather.usecase.GetLocationWeatherUseCase
import prus.justweatherapp.feature.locations.mapper.mapToUiModel
import prus.justweatherapp.feature.locations.model.LocationUiModel
import javax.inject.Inject

@HiltViewModel
class UserLocationListItemViewModel @Inject constructor(
    val getLocationWeatherUseCase: GetLocationWeatherUseCase,
) : ViewModel() {

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
                        onNewTimeZoneOffset(weather.timezoneOffset)
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
            _state.update { state ->
                state.copy(
                    timeState = TimeState.Success(
                        time = UiText.DynamicString(
                            getLocationCurrentTime(timezoneOffset).formatTime()
                        )
                    )
                )
            }
        }
    }

    override fun onCleared() {
        timeUpdater?.cancel()
        super.onCleared()
    }
}