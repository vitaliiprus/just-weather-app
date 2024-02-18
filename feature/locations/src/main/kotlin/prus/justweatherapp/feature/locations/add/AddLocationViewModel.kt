package prus.justweatherapp.feature.locations.add

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.domain.locations.usecase.AddUserLocationUseCase
import prus.justweatherapp.domain.locations.usecase.GetLocationByIdUseCase
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.feature.locations.add.navigation.AddLocationArgs
import prus.justweatherapp.feature.locations.mapper.mapToUiModel
import javax.inject.Inject

@HiltViewModel
class AddLocationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val getLocationByIdUseCase: GetLocationByIdUseCase,
    val addUserLocationUseCase: AddUserLocationUseCase,
) : ViewModel() {

    private val addLocationArgs: AddLocationArgs = AddLocationArgs(savedStateHandle)

    private val locationId = addLocationArgs.locationId
    private var location: Location? = null

    private val _addLocationUiState = MutableStateFlow(
        AddLocationUiState(
            locationDataState = LocationDataState.Loading,
            weatherDataState = WeatherDataState.Loading
        )
    )
    val addLocationUiState: StateFlow<AddLocationUiState> = _addLocationUiState

    init {
        viewModelScope.launch {
            _addLocationUiState.update { state ->
                state.copy(
                    locationDataState = getLocationState(
                        locationId = locationId
                    )
                )
            }
        }
    }

    private suspend fun getLocationState(
        locationId: String
    ): LocationDataState {
        location = getLocationByIdUseCase(locationId = locationId)
        return location?.let {
            LocationDataState.Success(
                location = it.mapToUiModel()
            )
        } ?: LocationDataState.Error(
            message = UiText.StringResource(R.string.error_cannot_find_location)
        )
    }

    fun onAddLocationClicked() {
        viewModelScope.launch {
            location?.let { it ->
                addUserLocationUseCase(it)
                _addLocationUiState.update { state ->
                    state.copy(
                        isLocationAdded = true
                    )
                }
            }
        }
    }
}