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
import prus.justweatherapp.core.ui.R
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.usecase.GetLocationDailyForecastUseCase
import prus.justweatherapp.feature.weather.location.forecast.daily.temprange.TempRangeModel

@HiltViewModel(assistedFactory = DailyForecastWeatherViewModel.ViewModelFactory::class)
class DailyForecastWeatherViewModel @AssistedInject constructor(
    @Assisted val locationId: String,
    getForecastWeatherUseCase: GetLocationDailyForecastUseCase
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
        return data.map {
            //TODO:
            DailyForecastWeatherUiModel(
                date = "FR, 29 March",
                conditionImageResId = R.drawable.mostlysunny,
                weatherConditions = UiText.DynamicString("Mostly sunny"),
                precipitationProb = "29%",
                tempRangeModel = TempRangeModel(
                    dayMinTemp = 7.0,
                    dayMaxTemp = 12.0,
                    rangeMinTemp = -2.0,
                    rangeMaxTemp = 12.0
                )
            )
        }
    }
}