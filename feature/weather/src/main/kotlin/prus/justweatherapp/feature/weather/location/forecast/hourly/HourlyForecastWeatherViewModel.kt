package prus.justweatherapp.feature.weather.location.forecast.hourly

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
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.usecase.GetLocationForecastWeatherUseCase

@HiltViewModel(assistedFactory = HourlyForecastWeatherViewModel.ViewModelFactory::class)
class HourlyForecastWeatherViewModel @AssistedInject constructor(
    @Assisted val locationId: String,
    getForecastWeatherUseCase: GetLocationForecastWeatherUseCase
) : ViewModel() {

    @AssistedFactory
    interface ViewModelFactory {
        fun create(locationId: String): HourlyForecastWeatherViewModel
    }

    val state: StateFlow<HourlyForecastWeatherUiState> =
        getForecastWeatherUseCase(locationId)
            .map { result ->
                when (result) {
                    is RequestResult.Error -> HourlyForecastWeatherUiState.Error(result.error?.message)
                    is RequestResult.Loading -> HourlyForecastWeatherUiState.Loading
                    is RequestResult.Success -> {
                        result.data?.let { data ->
                            HourlyForecastWeatherUiState.Success(weather = mapToUiModel(data))
                        } ?: HourlyForecastWeatherUiState.Error("Cannot get weather data")
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HourlyForecastWeatherUiState.Loading
            )

    private fun mapToUiModel(data: List<Weather>): List<HourlyForecastWeatherUiModel> {
        TODO("Not yet implemented")
    }
}