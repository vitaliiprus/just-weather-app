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
    val searchLocationsState by searchLocationsViewModel.state.collectAsStateWithLifecycle()

    val searchQuery by searchLocationsViewModel.searchQuery.collectAsStateWithLifecycle()

    Column {

        FindLocationsSearchBar(
            modifier = Modifier
                .padding(8.dp),
            searchQuery = searchQuery,
            onSearchQueryChanged = searchLocationsViewModel::onSearchQueryChanged,
            onSearchPressed = searchLocationsViewModel::onSearchPressed,
            onFocused = locationsViewModel::onSearchFocused,
            onCancelClicked = locationsViewModel::onSearchCancelClicked,
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

