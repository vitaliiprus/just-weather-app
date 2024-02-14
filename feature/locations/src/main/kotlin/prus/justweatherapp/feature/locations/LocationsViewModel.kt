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
            searchBarState = SearchBarState(
                searchQuery = "",
                cancelButtonState = CancelButtonState.Hidden
            ),
            currentScreen = CurrentLocationsScreen.UserLocations
        )
    )

    fun onSearchQueryChanged(query: String) {
        state = state.copy(
            searchBarState = state.searchBarState.copy(
                searchQuery = query,
            )
        )
    }

    fun onSearchFocused() {
        state = state.copy(
            currentScreen = CurrentLocationsScreen.SearchLocations,
            searchBarState = state.searchBarState.copy(
                cancelButtonState = CancelButtonState.Shown
            )
        )
    }

    fun onSearchCancelClicked() {
        state = state.copy(
            currentScreen = CurrentLocationsScreen.UserLocations,
            searchBarState = state.searchBarState.copy(
                searchQuery = "",
                cancelButtonState = CancelButtonState.Hidden
            )
        )
    }
}