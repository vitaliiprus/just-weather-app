package prus.justweatherapp.feature.locations.user

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import prus.justweatherapp.core.ui.components.MessageScreen
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.theme.AppTheme

@Composable
internal fun UserLocationsUi(
    state: UserLocationsScreenState
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
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
                    subtitle = stringResource(id = R.string.use_search_bar_hint)
                )
            }

            is UserLocationsScreenState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.locations) { location ->
                        UserLocationListItem(location)
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Empty user locations",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun UserLocationsUiPreview() {
    AppTheme {
        Surface {
            UserLocationsUi(
                state = UserLocationsScreenState.Empty
            )
        }
    }
}