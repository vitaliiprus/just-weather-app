package prus.justweatherapp.feature.locations.search

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import prus.justweatherapp.core.ui.components.MessageScreen
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.theme.AppTheme

@Composable
internal fun SearchLocationsListUi(
    state: SearchLocationScreenState,
    onLocationClicked: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = ColorPainter(MaterialTheme.colorScheme.background),
                contentScale = ContentScale.FillBounds
            )
    )
    {
        when (state) {

            is SearchLocationScreenState.Error -> {
//                TODO()
            }

            SearchLocationScreenState.Loading -> {
//                TODO()
            }

            is SearchLocationScreenState.Success -> {
                val locations = state.locations.collectAsLazyPagingItems()
                when (locations.loadState.refresh) {
                    is LoadState.Error -> {}
                    LoadState.Loading -> {}
                    is LoadState.NotLoading -> {
                        if (locations.itemCount == 0) {
                            MessageScreen(
                                title = stringResource(id = R.string.nothing_found),
                                subtitle = stringResource(id = R.string.nothing_found_hint),
                                imagePainter = painterResource(id = R.drawable.ic_close_circle)
                            )
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentPadding = PaddingValues(0.dp, 8.dp)
                            ) {
                                items(
                                    count = locations.itemCount,
                                    key = locations.itemKey { it.id }
                                ) { index ->
                                    locations[index]?.let { location ->
                                        SearchLocationListItem(
                                            location = location,
                                            onClick = onLocationClicked
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Loading search locations",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun SearchLocationsListUiPreview() {
    AppTheme {
        Surface {
            SearchLocationsListUi(
                state = SearchLocationScreenState.Loading,
                onLocationClicked = {}
            )
        }
    }
}