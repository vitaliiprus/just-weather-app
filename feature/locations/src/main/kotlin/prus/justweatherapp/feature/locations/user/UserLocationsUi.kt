package prus.justweatherapp.feature.locations.user

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import prus.justweatherapp.core.ui.components.MessageScreen
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.feature.locations.edit.EditLocationNameDialog
import prus.justweatherapp.theme.AppTheme

@Composable
internal fun UserLocationsUi(
    state: UserLocationsScreenState,
    onFabClicked: () -> Unit,
    onLocationNameEditClicked: (String) -> Unit,
    onEditLocationNameDialogDismiss: () -> Unit
) {
    val userLocationsLazyListState = rememberLazyListState()

    Scaffold(
        floatingActionButton = {
            AnimatedVisibility(
                visible = state.locationsState is UserLocationsState.Success,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {

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
        }
    ) { paddings ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = paddings.calculateStartPadding(LayoutDirection.Ltr),
                    end = paddings.calculateEndPadding(LayoutDirection.Ltr),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Crossfade(
                targetState = state.locationsState,
                label = "UserLocationsUi Crossfade"
            ) { locationsState ->
                when (locationsState) {
                    is UserLocationsState.Error -> {
                    }

                    UserLocationsState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    UserLocationsState.Empty -> {
                        MessageScreen(
                            title = stringResource(id = R.string.add_location),
                            subtitle = stringResource(id = R.string.use_search_bar_hint),
                            imagePainter = painterResource(id = R.drawable.ic_location_stroke)
                        )
                    }

                    is UserLocationsState.Success -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            state = userLocationsLazyListState,
                            contentPadding = PaddingValues(
                                start = 0.dp,
                                end = 0.dp,
                                top = 8.dp,
                                bottom = 100.dp
                            )
                        ) {
                            items(locationsState.locations) { location ->
                                UserLocationListItem(
                                    location = location,
                                    isEditing = state.isEditing,
                                    onEditClicked = onLocationNameEditClicked
                                )
                            }
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.colorScheme.background.copy(alpha = 0.0f),
                        )
                    )
                )
        )
    }

    state.editLocationNameDialogState.let { dialogState ->
        if (dialogState is EditLocationNameDialogState.Show)
            EditLocationNameDialog(
                locationId = dialogState.locationId,
                onDismissRequest = onEditLocationNameDialogDismiss
            )
    }
}

@PreviewLightDark
@Composable
private fun UserLocationsUiPreview() {
    AppTheme {
        Surface {
            UserLocationsUi(
                state = UserLocationsScreenState(
                    locationsState = UserLocationsState.Loading,
                    editLocationNameDialogState = EditLocationNameDialogState.Hide,
                    isEditing = false
                ),
                onFabClicked = {},
                onLocationNameEditClicked = {},
                onEditLocationNameDialogDismiss = {},
            )
        }
    }
}