package prus.justweatherapp.feature.locations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import prus.justweatherapp.feature.locations.user.UserLocationsScreenState
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
) : ViewModel() {

    private var previousUserLocationsCount: Int? = null

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

    fun observeUserLocations(userLocationsState: StateFlow<UserLocationsScreenState>) =
        viewModelScope.launch {
            userLocationsState.collect { state ->
                if (state is UserLocationsScreenState.Success) {
                    previousUserLocationsCount?.let {
                        if (state.locations.size > it)
                            onSearchCancelClicked()
                    }
                    previousUserLocationsCount = state.locations.size
                }
            }
        }
}