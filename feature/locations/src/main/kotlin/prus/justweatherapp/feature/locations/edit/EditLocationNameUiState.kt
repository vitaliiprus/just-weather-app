package prus.justweatherapp.feature.locations.edit

import prus.justweatherapp.core.ui.UiText

data class EditLocationNameUiState(
    val dialogState: EditLocationNameDialogState,
    val dialogNameValue: String,
    val showRestoreButton: Boolean,
    val isOkButtonEnabled: Boolean,
    val closeDialog: Boolean,
)

sealed interface EditLocationNameDialogState {
    data object Loading : EditLocationNameDialogState
    data class Error(val message: UiText) : EditLocationNameDialogState
    data object Success : EditLocationNameDialogState
}

