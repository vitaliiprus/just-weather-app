package prus.justweatherapp.feature.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import prus.justweatherapp.core.common.result.Result
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.domain.locations.usecase.GetUserLocationsUseCase
import prus.justweatherapp.feature.locations.mapper.mapToUiModels
import prus.justweatherapp.feature.locations.user.EditLocationNameDialogState
import prus.justweatherapp.feature.locations.user.UserLocationsScreenState
import prus.justweatherapp.feature.locations.user.UserLocationsState
import javax.inject.Inject

@HiltViewModel
class UserLocationsViewModel @Inject constructor(
    val getUserLocationsUseCase: GetUserLocationsUseCase
) : ViewModel() {

    private var _state: MutableStateFlow<UserLocationsScreenState> =
        MutableStateFlow(
            UserLocationsScreenState(
                locationsState = UserLocationsState.Loading,
                editLocationNameDialogState = EditLocationNameDialogState.Hide,
                isEditing = false
            )
        )

    var state: StateFlow<UserLocationsScreenState> = _state

    init {
        getUserLocations()
    }

    private fun getUserLocations() {
        viewModelScope.launch {
            getUserLocationsUseCase()
                .asResult()
                .collect { result ->
                    _state.update { state ->
                        state.copy(
                            locationsState = when (result) {

                                is Result.Error -> {
                                    UserLocationsState.Error(
                                        result.exception.message ?: result.exception.toString()
                                    )
                                }

                                Result.Loading -> {
                                    UserLocationsState.Loading
                                }

                                is Result.Success -> {
                                    if (result.data.isEmpty()) {
                                        UserLocationsState.Empty
                                    } else {
                                        UserLocationsState.Success(
                                            locations = result.data.mapToUiModels()
                                        )
                                    }
                                }

                            }
                        )
                    }
                }
        }
    }

    fun onEditClicked() {
        _state.update { state ->
            state.copy(
                isEditing = !state.isEditing
            )
        }
    }

    fun onLocationNameEditClicked(locationId: String) {
        _state.update { state ->
            state.copy(
                editLocationNameDialogState = EditLocationNameDialogState.Show(
                    locationId = locationId
                )
            )
        }
    }

    fun onEditLocationNameDialogDismiss() {
        _state.update { state ->
            state.copy(
                editLocationNameDialogState = EditLocationNameDialogState.Hide
            )
        }
    }

}