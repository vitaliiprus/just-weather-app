package prus.justweatherapp.feature.weather.location.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.LocalDateTime
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.core.common.util.TimeUpdater
import prus.justweatherapp.core.common.util.formatDateTime
import prus.justweatherapp.core.common.util.formatDuration
import prus.justweatherapp.core.common.util.formatTime
import prus.justweatherapp.core.common.util.getPercentageOfTimeBetween
import prus.justweatherapp.core.common.util.isBetween
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.usecase.GetLocationCurrentWeatherUseCase
import prus.justweatherapp.feature.weather.R
import prus.justweatherapp.feature.weather.location.current.daylight.DaylightUiModel
import prus.justweatherapp.feature.weather.mapper.getPressureString
import prus.justweatherapp.feature.weather.mapper.getTempMinMaxString
import prus.justweatherapp.feature.weather.mapper.getTempString
import prus.justweatherapp.feature.weather.mapper.getWeatherConditionImageResId
import prus.justweatherapp.feature.weather.mapper.getWeatherConditionsString
import prus.justweatherapp.feature.weather.mapper.getWindString
import kotlin.math.ceil
import kotlin.math.roundToInt

@HiltViewModel(assistedFactory = CurrentWeatherViewModel.ViewModelFactory::class)
class CurrentWeatherViewModel @AssistedInject constructor(
    @Assisted val locationId: String,
    getCurrentWeatherUseCase: GetLocationCurrentWeatherUseCase
) : ViewModel() {

    @AssistedFactory
    interface ViewModelFactory {
        fun create(locationId: String): CurrentWeatherViewModel
    }

    private var _timeState: MutableStateFlow<CurrentWeatherTimeUiState> =
        MutableStateFlow(CurrentWeatherTimeUiState.Loading)

    var timeState: StateFlow<CurrentWeatherTimeUiState> = _timeState

    private var timeUpdater: TimeUpdater? = null

    val state: StateFlow<CurrentWeatherUiState> =
        getCurrentWeatherUseCase(locationId)
            .onEach { result ->
                timeUpdater?.cancel()
                when (result) {
                    is RequestResult.Error -> CurrentWeatherTimeUiState.Error()
                    is RequestResult.Loading -> CurrentWeatherTimeUiState.Loading
                    is RequestResult.Success -> {
                        result.data?.let { data ->
                            timeUpdater = TimeUpdater(data.timezoneOffset) { newTime ->
                                _timeState.value = CurrentWeatherTimeUiState.Success(
                                    time = newTime.formatDateTime(),
                                    daylight = getDaylightData(data, newTime),
                                )
                            }
                        }
                    }
                }
            }
            .map { result ->
                when (result) {
                    is RequestResult.Error -> CurrentWeatherUiState.Error(result.error?.message)
                    is RequestResult.Loading -> CurrentWeatherUiState.Loading
                    is RequestResult.Success -> {
                        result.data?.let { data ->
                            CurrentWeatherUiState.Success(weather = mapToUiModel(data))
                        } ?: CurrentWeatherUiState.Error("Cannot get weather data")
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = CurrentWeatherUiState.Loading
            )

    private fun mapToUiModel(data: Weather): CurrentWeatherUiModel {
        return CurrentWeatherUiModel(
            temp = getTempString(data.temp, true, data.tempScale),
            feelsLike = UiText.StringResource(
                id = R.string.template_feels_like,
                args = arrayOf(getTempString(data.feelsLike, false))
            ),
            weatherConditions = getWeatherConditionsString(data.weatherConditions),
            conditionImageResId = getWeatherConditionImageResId(
                weatherConditions = data.weatherConditions,
                isDay = data.dateTime.time.isBetween(data.sunrise, data.sunset)
            ),
            //TODO: handle polar day and polar night
            sunrise = data.sunrise.formatTime(),
            sunset = data.sunset.formatTime(),
            tempMinMax = getTempMinMaxString(data.tempMin, data.tempMax),
            uvIndex = "1",
            pressure = getPressureString(data.pressure, data.pressureScale),
            precipitationProb = getPrecipitationProbString(data.probOfPrecipitations),
            humidity = "${data.humidity.roundToInt()}%",
            wind = getWindString(data.wind)
        )
    }

    private fun getDaylightData(weather: Weather, newTime: LocalDateTime): DaylightUiModel {
        return DaylightUiModel(
            text = weather.daylight.formatDuration(),
            percentage = newTime.time.getPercentageOfTimeBetween(weather.sunrise, weather.sunset),
            isDay = newTime.time.isBetween(weather.sunrise, weather.sunset)
        )
    }

    private fun getPrecipitationProbString(probOfPrecipitations: Double?): String {
        return if (probOfPrecipitations == null || probOfPrecipitations == 0.0)
            "0%"
        else "${ceil(probOfPrecipitations * 100).roundToInt()}%"

    }

    override fun onCleared() {
        timeUpdater?.cancel()
        super.onCleared()
    }
}