package prus.justweatherapp.feature.locations

import androidx.compose.animation.Crossfade
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
fun LocationsScreen(
    onSearchLocationClicked: (String) -> Unit
) {
    val locationsViewModel: LocationsViewModel = hiltViewModel()
    val userLocationsViewModel: UserLocationsViewModel = hiltViewModel()
    val searchLocationsViewModel: SearchLocationViewModel = hiltViewModel()

    val locationsScreenState = locationsViewModel.state
    val userLocationsState by userLocationsViewModel.state.collectAsStateWithLifecycle()
    val searchLocationsState = searchLocationsViewModel.state

    locationsViewModel.observeUserLocations(userLocationsViewModel.state)

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
            onFocused = {
                locationsViewModel.onSearchFocused()
                searchLocationsViewModel.onSearchFocused()
            },
            onCancelClicked = {
                locationsViewModel.onSearchCancelClicked()
                searchLocationsViewModel.onSearchCancelClicked()
            },
        )

        Crossfade(
            targetState = locationsScreenState.currentScreen,
            label = "LocationsScreen Crossfade"
        ) { state ->
            when (state) {

                CurrentLocationsScreen.SearchLocations -> {
                    SearchLocationsListUi(
                        state = searchLocationsState,
                        onLocationClicked = onSearchLocationClicked
                    )
                }

                CurrentLocationsScreen.UserLocations -> {
                    UserLocationsUi(
                        state = userLocationsState,
                        onFabClicked = userLocationsViewModel::onEditClicked,
                        onLocationNameEditClicked = userLocationsViewModel::onLocationNameEditClicked,
                        onLocationDeleteClicked = userLocationsViewModel::onLocationDeleteClicked,
                        onEditLocationNameDialogDismiss = userLocationsViewModel::onEditLocationNameDialogDismiss,
                        onLocationUndoDeleteClicked = userLocationsViewModel::onLocationUndoDeleteClicked,
                        onLocationDeletedMessageDismiss = userLocationsViewModel::onLocationDeletedMessageDismiss,
                        onDragFinish = userLocationsViewModel::onDragFinish,
                    )
                }
            }

        }

    }
}

