package prus.justweatherapp.feature.weather.location.forecast.daily

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DailyForecastWeatherUI(
    modifier: Modifier,
    locationId: String,
    viewModel: DailyForecastWeatherViewModel = hiltViewModel<DailyForecastWeatherViewModel,
            DailyForecastWeatherViewModel.ViewModelFactory>
        (key = locationId) { factory ->
        factory.create(locationId)
    },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DailyForecastWeatherUI(
        modifier = modifier,
        state = state
    )
}

@Composable
private fun DailyForecastWeatherUI(
    modifier: Modifier = Modifier,
    state: DailyForecastWeatherUiState
) {

}
