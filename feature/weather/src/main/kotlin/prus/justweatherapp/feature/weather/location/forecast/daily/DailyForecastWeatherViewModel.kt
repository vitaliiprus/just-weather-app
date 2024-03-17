package prus.justweatherapp.feature.weather.location.forecast.daily

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

@HiltViewModel(assistedFactory = DailyForecastWeatherViewModel.ViewModelFactory::class)
class DailyForecastWeatherViewModel @AssistedInject constructor(
    @Assisted val locationId: String,
    getForecastWeatherUseCase: GetLocationForecastWeatherUseCase
) : ViewModel() {

    @AssistedFactory
    interface ViewModelFactory {
        fun create(locationId: String): DailyForecastWeatherViewModel
    }

    val state: StateFlow<DailyForecastWeatherUiState> =
        getForecastWeatherUseCase(locationId)
            .map { result ->
                when (result) {
                    is RequestResult.Error -> DailyForecastWeatherUiState.Error(result.error?.message)
                    is RequestResult.Loading -> DailyForecastWeatherUiState.Loading
                    is RequestResult.Success -> {
                        result.data?.let { data ->
                            DailyForecastWeatherUiState.Success(weather = mapToUiModel(data))
                        } ?: DailyForecastWeatherUiState.Error("Cannot get weather data")
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DailyForecastWeatherUiState.Loading
            )

    private fun mapToUiModel(data: List<Weather>): List<DailyForecastWeatherUiModel> {
        TODO("Not yet implemented")
    }
}