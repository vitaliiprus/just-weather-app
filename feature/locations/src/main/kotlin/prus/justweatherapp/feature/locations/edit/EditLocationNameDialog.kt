package prus.justweatherapp.feature.locations.edit

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import prus.justweatherapp.core.ui.components.JwaButton
import prus.justweatherapp.core.ui.components.JwaOutlinedButton
import prus.justweatherapp.core.ui.components.JwaTextButton
import prus.justweatherapp.core.ui.components.JwaTextField
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.theme.JwaTheme
import prus.justweatherapp.theme.contentPaddings
import prus.justweatherapp.theme.dialogPaddings

@Composable
internal fun EditLocationNameDialog(
    locationId: String,
    onDismissRequest: () -> Unit,
    viewModel: EditLocationNameViewModel = hiltViewModel(),
) {
    LaunchedEffect(locationId) {
        viewModel.setLocationId(locationId)
    }

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.closeDialog) {
        onDismissRequest()
        viewModel.onDialogClosed()
    }

    EditLocationNameDialog(
        state = state,
        onDisplayNameChanged = viewModel::onDisplayNameChanged,
        onRestoreOriginalClicked = viewModel::onRestoreOriginalClicked,
        onRenameClicked = viewModel::onRenameClicked,
        onDismissRequest = viewModel::closeDialog,
    )
}

@Composable
private fun EditLocationNameDialog(
    state: EditLocationNameUiState,
    onDisplayNameChanged: (String) -> Unit,
    onRestoreOriginalClicked: () -> Unit,
    onRenameClicked: () -> Unit,
    onDismissRequest: () -> Unit,
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    if (state.dialogState is EditLocationNameDialogState.Success) {

        Dialog(
            onDismissRequest = onDismissRequest
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .dialogPaddings()
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.large
                    )
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = MaterialTheme.shapes.large
                    ),

                ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .contentPaddings(
                            top = 20.dp,
                            start = 20.dp,
                            end = 20.dp,
                        )
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.rename_location),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    JwaTextField(
                        modifier = Modifier
                            .animateContentSize()
                            .fillMaxWidth(),

                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done,
                            autoCorrect = false
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                                focusManager.clearFocus(true)
                            },
                        ),
                        textFieldValue = state.dialogNameValue,
                        onValueChanged = onDisplayNameChanged,
                        placeholderValue = stringResource(id = R.string.enter_location_name)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    if (state.showRestoreButton) {

                        JwaTextButton(
                            text = stringResource(id = R.string.restore_original_name),
                            onClick = onRestoreOriginalClicked
                        )

                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        JwaButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.rename),
                            enabled = state.isOkButtonEnabled,
                            onClick = onRenameClicked
                        )

                        Spacer(modifier = Modifier.size(4.dp))

                        JwaOutlinedButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(id = R.string.cancel),
                            onClick = onDismissRequest
                        )


                    }

                }
            }

        }

    }
}

@PreviewLightDark
@Composable
private fun EditLocationNameDialogPreview() {
    JwaTheme {
        Surface {
            EditLocationNameDialog(
                state = EditLocationNameUiState(
                    dialogState = EditLocationNameDialogState.Success,
                    dialogNameValue = "Saint Petersburg",
                    isOkButtonEnabled = true,
                    showRestoreButton = true,
                    closeDialog = false
                ),
                onDisplayNameChanged = {},
                onRestoreOriginalClicked = {},
                onRenameClicked = {},
                onDismissRequest = {}
            )
        }
    }
}

