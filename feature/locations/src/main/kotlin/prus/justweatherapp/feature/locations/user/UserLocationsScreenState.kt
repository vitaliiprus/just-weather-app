package prus.justweatherapp.feature.locations.user

import prus.justweatherapp.feature.locations.model.LocationUiModel

data class UserLocationsScreenState(
    val locationsState: UserLocationsState,
    val editLocationNameDialogState: EditLocationNameDialogState,
    val isEditing: Boolean
)

sealed interface UserLocationsState {
    data object Loading : UserLocationsState
    data class Error(val message: String) : UserLocationsState
    data object Empty : UserLocationsState
    data class Success(
        val locations: List<LocationUiModel>
    ) : UserLocationsState
}

sealed interface EditLocationNameDialogState {
    data object Hide : EditLocationNameDialogState
    data class Show(
        val locationId: String
    ) : EditLocationNameDialogState
}