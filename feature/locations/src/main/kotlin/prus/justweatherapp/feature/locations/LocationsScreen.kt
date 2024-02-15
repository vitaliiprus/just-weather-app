package prus.justweatherapp.feature.locations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import prus.justweatherapp.feature.locations.search.SearchLocationsListUi
import prus.justweatherapp.feature.locations.user.UserLocationsUi

@Composable
fun LocationsScreen() {
    val locationsViewModel: LocationsViewModel = hiltViewModel()
    val userLocationsViewModel: UserLocationsViewModel = hiltViewModel()
    val searchLocationsViewModel: SearchLocationViewModel = hiltViewModel()

    val locationsScreenState = locationsViewModel.state
    val userLocationsState by userLocationsViewModel.state.collectAsStateWithLifecycle()
    val searchLocationsState = searchLocationsViewModel.state

    Column {

        FindLocationsSearchBar(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                ),
            state = locationsScreenState.searchBarState,
            onSearchQueryChanged = {
                locationsViewModel.onSearchQueryChanged(it)
                searchLocationsViewModel.onSearchQueryChanged(it)
            },
            onSearchPressed = searchLocationsViewModel::onSearchPressed,
            onFocused = {
                locationsViewModel.onSearchFocused()
                searchLocationsViewModel.onSearchFocused()
            },
            onCancelClicked = {
                locationsViewModel.onSearchCancelClicked()
                searchLocationsViewModel.onSearchCancelClicked()
            },
        )

        when (locationsScreenState.currentScreen) {

            CurrentLocationsScreen.SearchLocations -> {
                SearchLocationsListUi(
                    state = searchLocationsState
                )
            }

            CurrentLocationsScreen.UserLocations -> {
                UserLocationsUi(
                    state = userLocationsState
                )
            }
        }
    }
}

