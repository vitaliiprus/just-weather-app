package prus.justweatherapp.feature.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.locations.usecase.AddUserLocationUseCase
import prus.justweatherapp.domain.locations.usecase.DeleteUserLocationUseCase
import prus.justweatherapp.domain.locations.usecase.GetUserLocationsUseCase
import prus.justweatherapp.domain.locations.usecase.UpdateUserLocationOrderIndexUseCase
import prus.justweatherapp.feature.locations.mapper.mapToUiModels
import prus.justweatherapp.feature.locations.user.EditLocationNameDialogState
import prus.justweatherapp.feature.locations.user.LocationDeletedMessageState
import prus.justweatherapp.feature.locations.user.UserLocationsScreenState
import prus.justweatherapp.feature.locations.user.UserLocationsState
import javax.inject.Inject

@HiltViewModel
class UserLocationsViewModel @Inject constructor(
    val getUserLocationsUseCase: GetUserLocationsUseCase,
    val deleteUserLocationUseCase: DeleteUserLocationUseCase,
    val addUserLocationUseCase: AddUserLocationUseCase,
    val updateUserLocationOrderIndexUseCase: UpdateUserLocationOrderIndexUseCase,
) : ViewModel() {

    private var _state: MutableStateFlow<UserLocationsScreenState> =
        MutableStateFlow(
            UserLocationsScreenState(
                locationsState = UserLocationsState.Loading,
                editLocationNameDialogState = EditLocationNameDialogState.Hide,
                locationDeletedMessageState = LocationDeletedMessageState.Hide,
                isEditing = false
            )
        )

    var state: StateFlow<UserLocationsScreenState> = _state

    init {
        getUserLocations()
    }

    private fun getUserLocations() {
        viewModelScope.launch(Dispatchers.IO) {
            getUserLocationsUseCase()
                .asResult()
                .collect { result ->
                    _state.value = state.value.copy(
                        locationsState = result.getOrNull()?.let { data ->
                            if (data.isEmpty()) {
                                UserLocationsState.Empty
                            } else {
                                UserLocationsState.Success(
                                    locations = data.mapToUiModels()
                                )
                            }
                        } ?: UserLocationsState.Error(
                            result.exceptionOrNull()?.message ?: ""
                        )
                    )
                }
        }
    }

    fun onEditClicked() {
        _state.value = state.value.copy(
            isEditing = !state.value.isEditing
        )
    }

    fun onLocationNameEditClicked(locationId: String) {
        _state.value = state.value.copy(
            editLocationNameDialogState = EditLocationNameDialogState.Show(
                locationId = locationId
            )
        )
    }

    fun onLocationDeleteClicked(locationId: String) {
        viewModelScope.launch {
            deleteUserLocationUseCase(locationId).let { result ->
                val data = result.getOrNull()
                _state.value = state.value.copy(
                    locationDeletedMessageState =
                    if (result.isSuccess && data != null) {
                        LocationDeletedMessageState.ShowUndo(
                            locationId = data.first,
                            locationName = data.second
                        )
                    } else {
                        LocationDeletedMessageState.ShowError(
                            message = UiText.StringResource(R.string.cannot_delete_location)
                        )
                    }
                )
            }
        }
    }

    fun onLocationUndoDeleteClicked(locationId: String) {
        viewModelScope.launch {
            addUserLocationUseCase(locationId).let { result ->
                _state.value = state.value.copy(
                    locationDeletedMessageState = if (result.isSuccess)
                        LocationDeletedMessageState.Hide
                    else
                        LocationDeletedMessageState.ShowError(
                            message = UiText.StringResource(R.string.cannot_find_location)
                        )
                )
            }
        }
    }

    fun onLocationDeletedMessageDismiss() {
        _state.value = state.value.copy(
            locationDeletedMessageState = LocationDeletedMessageState.Hide
        )
    }

    fun onEditLocationNameDialogDismiss() {
        _state.value = state.value.copy(
            editLocationNameDialogState = EditLocationNameDialogState.Hide
        )
    }

    fun onDragFinish(fromIndex: Int, toIndex: Int) {
        viewModelScope.launch {
            state.value.locationsState.let { locationsState ->
                if (locationsState is UserLocationsState.Success) {
                    locationsState.locations.getOrNull(fromIndex)?.let { fromLocation ->
                        locationsState.locations.getOrNull(toIndex)?.let { toLocation ->
                            updateUserLocationOrderIndexUseCase(fromLocation.id, toLocation.id)
                        }
                    }
                }
            }
        }
    }
}