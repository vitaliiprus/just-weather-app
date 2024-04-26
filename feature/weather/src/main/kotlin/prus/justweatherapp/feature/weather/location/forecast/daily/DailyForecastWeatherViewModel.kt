package prus.justweatherapp.feature.weather.location.forecast.daily

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import prus.justweatherapp.core.common.result.RequestResult
import prus.justweatherapp.core.common.util.LocaleChangeListener
import prus.justweatherapp.core.common.util.formatDailyDate
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.usecase.GetLocationDailyForecastUseCase
import prus.justweatherapp.feature.weather.location.forecast.daily.temprange.TempRangeModel
import prus.justweatherapp.feature.weather.mapper.getPrecipitationProbString
import prus.justweatherapp.feature.weather.mapper.getWeatherConditionImageResId
import prus.justweatherapp.feature.weather.mapper.getWeatherConditionsString

@HiltViewModel(assistedFactory = DailyForecastWeatherViewModel.ViewModelFactory::class)
class DailyForecastWeatherViewModel @AssistedInject constructor(
    @Assisted val locationId: String,
    getForecastWeatherUseCase: GetLocationDailyForecastUseCase,
    localeChangeListener: LocaleChangeListener
) : ViewModel() {

    @AssistedFactory
    interface ViewModelFactory {
        fun create(locationId: String): DailyForecastWeatherViewModel
    }

    val state: StateFlow<DailyForecastWeatherUiState> =
        getForecastWeatherUseCase(locationId)
            .combine(localeChangeListener.localeState) { result, _ ->
                result
            }
            .map { result ->
                when (result) {
                    is RequestResult.Error -> DailyForecastWeatherUiState.Error(result.error?.message)
                    is RequestResult.Loading -> DailyForecastWeatherUiState.Loading
                    is RequestResult.Success -> {
                        result.data?.let { data ->
                            val rangeMin = data.minWith(compareBy { it.tempMin!! }).tempMin!!
                            val rangeMax = data.maxWith(compareBy { it.tempMax!! }).tempMax!!
                            DailyForecastWeatherUiState.Success(
                                weather = mapToUiModel(data, rangeMin, rangeMax)
                            )
                        } ?: DailyForecastWeatherUiState.Error("Cannot get weather data")
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = DailyForecastWeatherUiState.Loading
            )

    private fun mapToUiModel(
        data: List<Weather>,
        rangeMin: Double,
        rangeMax: Double
    ): List<DailyForecastWeatherUiModel> {
        return data.map { weather ->
            DailyForecastWeatherUiModel(
                date = weather.dateTime.date.formatDailyDate(),
                conditionImageResId = getWeatherConditionImageResId(weather.weatherConditions),
                weatherConditions = getWeatherConditionsString(weather.weatherConditions),
                precipitationProb = getPrecipitationProbString(weather.probOfPrecipitations),
                tempRangeModel = TempRangeModel(
                    dayMinTemp = weather.tempMin!!,
                    dayMaxTemp = weather.tempMax!!,
                    rangeMinTemp = rangeMin,
                    rangeMaxTemp = rangeMax
                ),
            )
        }
    }
}