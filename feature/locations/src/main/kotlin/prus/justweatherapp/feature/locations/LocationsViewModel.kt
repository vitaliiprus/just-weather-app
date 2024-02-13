package prus.justweatherapp.feature.locations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
) : ViewModel() {

    var state by mutableStateOf(
        LocationsState(
            currentScreen = CurrentLocationsScreen.UserLocations
        )
    )

    fun onSearchFocused() {
        state = state.copy(currentScreen = CurrentLocationsScreen.SearchLocations)
    }
    fun onSearchCancelClicked() {
        state = state.copy(currentScreen = CurrentLocationsScreen.UserLocations)
    }
}