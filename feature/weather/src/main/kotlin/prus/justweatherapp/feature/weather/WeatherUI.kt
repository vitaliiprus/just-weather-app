package prus.justweatherapp.feature.weather

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import prus.justweatherapp.core.ui.components.MessageScreen
import prus.justweatherapp.feature.weather.location.LocationWeatherUI
import prus.justweatherapp.theme.AppTheme

@Composable
fun WeatherUI(
    onFindLocationsClick: () -> Unit
) {
    val viewModel: WeatherViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    WeatherUI(
        state = state,
        onFindLocationsClick = onFindLocationsClick
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WeatherUI(
    state: WeatherUiState,
    onFindLocationsClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    )
    {
        val context = LocalContext.current

        when (state) {
            is WeatherUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }

            is WeatherUiState.Loading -> {
                CircularProgressIndicator()
            }

            WeatherUiState.Empty -> {
                MessageScreen(
                    title = stringResource(id = R.string.empty_title),
                    subtitle = stringResource(id = R.string.empty_subtitle),
                    imagePainter = painterResource(id = R.drawable.ic_weather),
                    buttonText = stringResource(id = R.string.find_locations),
                    onButtonClick = onFindLocationsClick
                )
            }

            is WeatherUiState.Success -> {
                val pagerState = rememberPagerState(
                    pageCount = { state.locationIdsNames.size},
                    initialPage = state.initialPage
                )

                HorizontalPager(
                    state = pagerState
                ) { pageIndex ->
                    LocationWeatherUI(
                        locationId = state.locationIdsNames[pageIndex].first,
                        locationName = state.locationIdsNames[pageIndex].second,
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun WeatherUILoadingPreview() {
    AppTheme {
        Surface {
            WeatherUI(
                state = WeatherUiState.Loading,
                onFindLocationsClick = {}
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun WeatherUIEmptyPreview() {
    AppTheme {
        Surface {
            WeatherUI(
                state = WeatherUiState.Empty,
                onFindLocationsClick = {}
            )
        }
    }
}