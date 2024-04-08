package prus.justweatherapp.feature.weather

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.domain.locations.usecase.GetUserLocationsUseCase
import prus.justweatherapp.feature.weather.navigation.WeatherLocationArgs
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getUserLocationsUseCase: GetUserLocationsUseCase
) : ViewModel() {

    private val weatherLocationArgs: WeatherLocationArgs = WeatherLocationArgs(savedStateHandle)

    private val locationId = weatherLocationArgs.locationId

    val state: StateFlow<WeatherUiState> =
        getUserLocationsUseCase()
            .asResult()
            .map { result ->
                result.getOrNull()?.let { data ->
                    if (data.isEmpty()) {
                        WeatherUiState.Empty
                    } else {
                        val locationIdsNames = data.map { it.id to it.displayName }
                        val initialPage = locationIdsNames
                            .indexOfFirst { it.first == locationId }
                            .coerceAtLeast(0)
                        WeatherUiState.Success(
                            locationIdsNames = locationIdsNames,
                            initialPage = initialPage
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