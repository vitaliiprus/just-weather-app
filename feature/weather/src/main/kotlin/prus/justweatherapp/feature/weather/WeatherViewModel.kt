package prus.justweatherapp.feature.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.domain.locations.usecase.GetUserLocationsUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    getUserLocationsUseCase: GetUserLocationsUseCase
) : ViewModel() {

    val state: StateFlow<WeatherUiState> =
        getUserLocationsUseCase()
            .asResult()
            .map { result ->
                result.getOrNull()?.let { data ->
                    if (data.isEmpty()) {
                        WeatherUiState.Empty
                    } else {
                        WeatherUiState.Success(
                            locationIds = data.map { it.id }
                        )
                    }
                } ?: WeatherUiState.Error(
                    result.exceptionOrNull()?.message ?: ""
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = WeatherUiState.Loading
            )
}