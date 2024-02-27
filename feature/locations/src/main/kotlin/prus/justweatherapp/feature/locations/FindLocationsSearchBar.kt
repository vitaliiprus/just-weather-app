package prus.justweatherapp.feature.locations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import prus.justweatherapp.core.ui.components.JwaTextButton
import prus.justweatherapp.core.ui.components.JwaTextField
import prus.justweatherapp.theme.AppTheme

@Composable
internal fun FindLocationsSearchBar(
    modifier: Modifier = Modifier,
    state: SearchBarState,
    onSearchQueryChanged: (String) -> Unit,
    onFocused: () -> Unit = {},
    onCancelClicked: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        focusRequester.freeFocus()
    }

    val cancelButtonVisible = state.cancelButtonState == CancelButtonState.Shown

    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(54.dp),
    ) {

        AnimatedVisibility(
            visible = cancelButtonVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                JwaTextButton(
                    text = stringResource(id = R.string.cancel)
                ) {
                    focusManager.clearFocus(true)
                    onCancelClicked()
                }
            }
        }

        JwaTextField(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth(
                    if (cancelButtonVisible)
                        0.75f
                    else
                        1f
                )
                .focusRequester(focusRequester)
                .onKeyEvent {
                    if (it.key == Key.Enter) {
                        onSearchExplicitlyTriggered()
                        true
                    } else {
                        false
                    }
                },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchExplicitlyTriggered()
                },
            ),
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "search icon"
                )
            },
            placeholderValue = stringResource(id = R.string.find_locations),
            textFieldValue = state.searchQuery,
            onValueChanged = onSearchQueryChanged,
            onFocused = onFocused
        )

    }
}

@PreviewLightDark
@Composable
private fun FindLocationsSearchBarEmptyPreview() {
    AppTheme {
        Surface {
            FindLocationsSearchBar(
                state = SearchBarState(
                    searchQuery = "",
                    cancelButtonState = CancelButtonState.Hidden
                ),
                onSearchQueryChanged = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun FindLocationsSearchBarTextPreview() {
    AppTheme {
        Surface {
            FindLocationsSearchBar(
                state = SearchBarState(
                    searchQuery = "123",
                    cancelButtonState = CancelButtonState.Shown
                ),
                onSearchQueryChanged = {}
            )
        }
    }
}