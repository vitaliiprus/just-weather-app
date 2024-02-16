package prus.justweatherapp.feature.locations

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import prus.justweatherapp.core.ui.components.JwaTextButton
import prus.justweatherapp.theme.AppTheme

@Composable
internal fun FindLocationsSearchBar(
    modifier: Modifier = Modifier,
    state: SearchBarState,
    onSearchQueryChanged: (String) -> Unit,
    onSearchPressed: () -> Unit,
    onFocused: () -> Unit = {},
    onCancelClicked: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        focusRequester.freeFocus()
        onSearchPressed()
    }

    val cancelButtonVisible = state.cancelButtonState == CancelButtonState.Shown
    val clearButtonVisible = state.searchQuery.isNotEmpty()

    Box(
        modifier = Modifier
            .then(modifier)
            .fillMaxWidth()
            .height(54.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(12.dp),
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

        TextField(
            modifier = Modifier
                .animateContentSize()
                .fillMaxWidth(
                    if (cancelButtonVisible)
                        0.75f
                    else
                        1f
                )
                .focusRequester(focusRequester)
                .onFocusChanged {
                    if (it.isFocused)
                        onFocused.invoke()
                }
                .onKeyEvent {
                    if (it.key == Key.Enter) {
                        onSearchExplicitlyTriggered()
                        true
                    } else {
                        false
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onTertiaryContainer,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.onTertiary,
            ),
            textStyle = MaterialTheme.typography.bodyLarge,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchExplicitlyTriggered()
                },
            ),
            value = state.searchQuery,
            onValueChange = onSearchQueryChanged,
            leadingIcon = {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    tint = MaterialTheme.colorScheme.secondary,
                    contentDescription = "search icon"
                )
            },
            trailingIcon = {
                AnimatedVisibility(
                    visible = clearButtonVisible,
                    enter = fadeIn(animationSpec = tween(150))
                            + scaleIn(animationSpec = tween(100)),
                    exit = fadeOut(animationSpec = tween(150))
                            + scaleOut(animationSpec = tween(100))
                ) {
                    Icon(
                        modifier = Modifier
                            .size(28.dp)
                            .padding(4.dp)
                            .clickable {
                                onSearchQueryChanged("")
                            },
                        painter = painterResource(id = R.drawable.ic_close_circle),
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = "search icon"
                    )
                }
            },
            placeholder = {
                Text(
                    text = stringResource(id = R.string.find_locations),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            singleLine = true
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun FindLocationsSearchBarEmptyPreview() {
    AppTheme {
        Surface {
            FindLocationsSearchBar(
                state = SearchBarState(
                    searchQuery = "",
                    cancelButtonState = CancelButtonState.Hidden
                ),
                onSearchQueryChanged = {},
                onSearchPressed = {}
            )
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun FindLocationsSearchBarTextPreview() {
    AppTheme {
        Surface {
            FindLocationsSearchBar(
                state = SearchBarState(
                    searchQuery = "123",
                    cancelButtonState = CancelButtonState.Shown
                ),
                onSearchQueryChanged = {},
                onSearchPressed = {}
            )
        }
    }
}