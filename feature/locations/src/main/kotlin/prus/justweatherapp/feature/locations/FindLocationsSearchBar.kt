package prus.justweatherapp.feature.locations

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import prus.justweatherapp.theme.AppTheme

@Composable
internal fun FindLocationsSearchBar(
    searchQuery: String = "",
    onSearchQueryChanged: (String) -> Unit,
    onSearchPressed: () -> Unit
) {
//    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val onSearchExplicitlyTriggered = {
        keyboardController?.hide()
        onSearchPressed()
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
//            .padding(8.dp)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .onKeyEvent {
                    if (it.key == Key.Enter) {
                        onSearchExplicitlyTriggered()
                        true
                    } else {
                        false
                    }
                },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            ),
            shape = RoundedCornerShape(8.dp),
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_search),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = "search icon"
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchExplicitlyTriggered()
                },
            ),
            singleLine = true
        )

    }
}

@Preview(
    name = "Empty user locations",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun FindLocationsSearchBarPreview() {
    AppTheme {
        Surface {
            FindLocationsSearchBar(
                searchQuery = "123",
                onSearchQueryChanged = {},
                onSearchPressed = {}
            )
        }
    }
}