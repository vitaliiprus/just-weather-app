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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import prus.justweatherapp.core.ui.components.JwaButton
import prus.justweatherapp.feature.weather.location.LocationWeatherUI
import prus.justweatherapp.theme.AppTheme

@Composable
fun WeatherUI() {
    val viewModel: WeatherViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    WeatherUI(
        state = state
    )

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WeatherUI(
    state: WeatherUiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    )
    {
        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState(
            pageCount = { if (state is WeatherUiState.Success) state.locationIdsNames.size else 0 }
        )

        when (state) {
            is WeatherUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }

            is WeatherUiState.Loading -> {
                CircularProgressIndicator()
            }

            WeatherUiState.Empty -> {
                JwaButton(
                    text = stringResource(id = R.string.find_locations)
                )
            }

            is WeatherUiState.Success -> {
                LaunchedEffect(state.initialPage) {
                    coroutineScope.launch {
                        pagerState.scrollToPage(state.initialPage)
                    }
                }

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
                state = WeatherUiState.Loading
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
                state = WeatherUiState.Empty
            )
        }
    }
}