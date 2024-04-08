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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import prus.justweatherapp.core.ui.components.MessageScreen
import prus.justweatherapp.core.ui.dragdrop.DragDropLazyColumn
import prus.justweatherapp.core.ui.dragdrop.rememberDragDropListState
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.feature.locations.edit.EditLocationNameDialog
import prus.justweatherapp.feature.locations.user.listitem.UserLocationListItem
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.accent

@Composable
internal fun UserLocationsUi(
    state: UserLocationsScreenState,
    onFabClicked: () -> Unit,
    onLocationClicked: (String) -> Unit,
    onLocationNameEditClicked: (String) -> Unit,
    onLocationDeleteClicked: (String) -> Unit,
    onLocationUndoDeleteClicked: (String) -> Unit,
    onLocationDeletedMessageDismiss: () -> Unit,
    onEditLocationNameDialogDismiss: () -> Unit,
    onDragFinish: (Int, Int) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val dragDropListState = rememberDragDropListState(
        onDragFinish = onDragFinish
    ).also { it.dragDropEnabled = false }

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
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionColor = accent,
                    snackbarData = data
                )
            }
        }
    ) { paddings ->

        when (state.locationDeletedMessageState) {
            LocationDeletedMessageState.Hide -> {}
            is LocationDeletedMessageState.ShowError -> {
                MessageSnackbar(
                    message = state.locationDeletedMessageState.message.asString(),
                    snackbarHostState = snackbarHostState,
                    onDismissed = onLocationDeletedMessageDismiss
                )
            }

            is LocationDeletedMessageState.ShowUndo -> {
                UndoDeleteSnackbar(
                    locationId = state.locationDeletedMessageState.locationId,
                    locationName = state.locationDeletedMessageState.locationName,
                    snackbarHostState = snackbarHostState,
                    onUndoClicked = onLocationUndoDeleteClicked,
                    onDismissed = onLocationDeletedMessageDismiss
                )
            }
        }

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
                        MessageSnackbar(
                            message = locationsState.message,
                            snackbarHostState = snackbarHostState
                        )
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

                        DragDropLazyColumn(
                            modifier = Modifier
                                .fillMaxSize(),
                            items = locationsState.locations,
                            dragDropListState = dragDropListState,
                            contentPadding = PaddingValues(
                                start = 0.dp,
                                end = 0.dp,
                                top = 8.dp,
                                bottom = 100.dp
                            )
                        ) { location ->
                            UserLocationListItem(
                                location = location,
                                isEditing = state.isEditing,
                                onLocationClicked = onLocationClicked,
                                onEditClicked = onLocationNameEditClicked,
                                onDeleteClicked = onLocationDeleteClicked,
                                onDragDropStateChanged = {
                                    dragDropListState.dragDropEnabled = it
                                },
                            )
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


@Composable
private fun UndoDeleteSnackbar(
    locationId: String,
    locationName: String,
    snackbarHostState: SnackbarHostState,
    onUndoClicked: (String) -> Unit,
    onDismissed: () -> Unit,
) {
    val message = stringResource(R.string.template_location_deleted, locationName)
    val action = stringResource(R.string.undo)

    LaunchedEffect(locationId) {
        when (snackbarHostState.showSnackbar(
            message = message,
            actionLabel = action,
            duration = SnackbarDuration.Long
        )) {
            SnackbarResult.Dismissed -> onDismissed()
            SnackbarResult.ActionPerformed -> onUndoClicked(locationId)
        }
    }
}

@Composable
private fun MessageSnackbar(
    message: String,
    snackbarHostState: SnackbarHostState,
    onDismissed: () -> Unit = {},
) {
    LaunchedEffect(message) {
        when (snackbarHostState.showSnackbar(
            message = message
        )) {
            SnackbarResult.Dismissed -> onDismissed()
            else -> {}
        }
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
                    locationDeletedMessageState = LocationDeletedMessageState.Hide,
                    isEditing = false
                ),
                onFabClicked = {},
                onLocationClicked = {},
                onLocationNameEditClicked = {},
                onLocationDeleteClicked = {},
                onLocationUndoDeleteClicked = {},
                onLocationDeletedMessageDismiss = {},
                onEditLocationNameDialogDismiss = {},
                onDragFinish = { _, _ -> },
            )
        }
    }
}