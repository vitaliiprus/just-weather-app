package prus.justweatherapp.feature.locations.user

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import prus.justweatherapp.core.ui.components.MessageScreen
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.theme.AppTheme

@Composable
internal fun UserLocationsUi(
    state: UserLocationsScreenState,
    onFabClicked: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            if (state is UserLocationsScreenState.Success)
                FloatingActionButton(
                    onClick = onFabClicked
                ) {
                    AnimatedContent(
                        targetState = state.isEditing,
                        label = ""
                    ) { isEditing ->
                        Icon(
                            painter = painterResource(
                                id = if (isEditing)
                                    R.drawable.ic_check
                                else
                                    R.drawable.ic_edit
                            ),
                            contentDescription = stringResource(id = R.string.edit_locations)
                        )
                    }
                }
        }
    ) { paddings ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            when (state) {
                is UserLocationsScreenState.Error -> {
//                TODO()
                }

                UserLocationsScreenState.Loading -> {
//                TODO()
                }

                UserLocationsScreenState.Empty -> {
                    MessageScreen(
                        title = stringResource(id = R.string.add_location),
                        subtitle = stringResource(id = R.string.use_search_bar_hint),
                        imagePainter = painterResource(id = R.drawable.ic_location_stroke)
                    )
                }

                is UserLocationsScreenState.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentPadding = PaddingValues(0.dp, 8.dp)
                    ) {
                        items(state.locations) { location ->
                            UserLocationListItem(
                                location = location,
                                isEditing = state.isEditing
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun UserLocationsUiPreview() {
    AppTheme {
        Surface {
            UserLocationsUi(
                state = UserLocationsScreenState.Empty,
                onFabClicked = {}
            )
        }
    }
}