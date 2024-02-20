package prus.justweatherapp.feature.locations.user

import prus.justweatherapp.feature.locations.model.LocationUiModel

sealed interface UserLocationsScreenState {
    data object Loading : UserLocationsScreenState
    data class Error(val message: String) : UserLocationsScreenState
    data object Empty : UserLocationsScreenState
    data class Success(
        val locations: List<LocationUiModel>,
        val isEditing: Boolean
    ) : UserLocationsScreenState
}