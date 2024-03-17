package prus.justweatherapp.feature.weather.location.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.domain.weather.model.TempScale
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.usecase.GetLocationCurrentWeatherUseCase
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

    val state: StateFlow<CurrentWeatherUiState> =
        getCurrentWeatherUseCase(locationId)
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
            feelsLike = getTempString(data.feelsLike, false),
            tempMinMax = getTempMinMaxString(data.tempMin, data.tempMax)
        )
    }

    private fun getTempString(
        temp: Double,
        withScaleUnits: Boolean = false,
        tempScale: TempScale? = null
    ): String {
        if (!withScaleUnits) {
            return "${temp.roundToInt()}º"
        } else {
            requireNotNull(tempScale).let {
                return when (it) {
                    TempScale.KELVIN -> "${temp.roundToInt()}K"
                    TempScale.CELSIUS -> "${temp.roundToInt()}ºC"
                    TempScale.FAHRENHEIT -> "${temp.roundToInt()}ºF"
                }
            }
        }
    }

    private fun getTempMinMaxString(
        tempMin: Double,
        tempMax: Double,
    ): String {
        return "↓${getTempString(tempMin, false)} " +
                "↑${getTempString(tempMax, false)}"
    }
}