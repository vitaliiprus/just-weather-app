package prus.justweatherapp.feature.locations.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.domain.locations.usecase.GetUserLocationByIdUseCase
import prus.justweatherapp.domain.locations.usecase.RestoreUserLocationDisplayNameUseCase
import prus.justweatherapp.domain.locations.usecase.UpdateUserLocationDisplayNameUseCase
import javax.inject.Inject

@HiltViewModel
class EditLocationNameViewModel @Inject constructor(
    private val getUserLocationByIdUseCase: GetUserLocationByIdUseCase,
    private val updateUserLocationDisplayNameUseCase: UpdateUserLocationDisplayNameUseCase,
    private val restoreUserLocationDisplayNameUseCase: RestoreUserLocationDisplayNameUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(
        initialState()
    )

    private fun initialState(): EditLocationNameUiState {
        return EditLocationNameUiState(
            dialogState = EditLocationNameDialogState.Loading,
            dialogNameValue = "",
            showRestoreButton = false,
            isOkButtonEnabled = false,
            closeDialog = false,
        )
    }

    val state: StateFlow<EditLocationNameUiState> = _state

    private var location: Location? = null

    fun setLocationId(locationId: String) {
        viewModelScope.launch {
            location = getUserLocationByIdUseCase(locationId = locationId)
            location?.let {
                _state.value = state.value.copy(
                    dialogState = EditLocationNameDialogState.Success,
                    dialogNameValue = it.displayName,
                    showRestoreButton = it.isDisplayNameChanged(),
                    isOkButtonEnabled = true
                )
            } ?: kotlin.run {
                _state.value = state.value.copy(
                    dialogState = EditLocationNameDialogState.Error(
                        message = UiText.DynamicString("")
                    )
                )
            }
        }
    }

    fun onDisplayNameChanged(value: String) {
        _state.value = state.value.copy(
            dialogState = state.value.dialogState,
            dialogNameValue = value,
            isOkButtonEnabled = value.isNotBlank()
        )
    }

    fun onRestoreOriginalClicked() {
        viewModelScope.launch {
            location?.let {
                restoreUserLocationDisplayNameUseCase(it.id)
                closeDialog()
            }
        }
    }

    fun onRenameClicked() {
        viewModelScope.launch {
            _state.value.dialogNameValue.trim().let { newDisplayName ->
                if (newDisplayName.isBlank())
                    return@launch
                location?.let { location ->
                    updateUserLocationDisplayNameUseCase(location.id, newDisplayName)
                    closeDialog()
                }
            }
        }
    }

    fun closeDialog() {
        _state.value = state.value.copy(
            closeDialog = true
        )
    }

    fun onDialogClosed() {
        _state.value = initialState()
    }
}