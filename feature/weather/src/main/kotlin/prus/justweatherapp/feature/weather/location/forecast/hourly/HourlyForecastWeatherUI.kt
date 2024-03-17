package prus.justweatherapp.feature.weather.location.forecast.hourly

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HourlyForecastWeatherUI(
    modifier: Modifier,
    locationId: String,
    viewModel: HourlyForecastWeatherViewModel = hiltViewModel<HourlyForecastWeatherViewModel,
            HourlyForecastWeatherViewModel.ViewModelFactory>
        (key = locationId) { factory ->
        factory.create(locationId)
    },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HourlyForecastWeatherUI(
        modifier = modifier,
        state = state
    )
}

@Composable
private fun HourlyForecastWeatherUI(
    modifier: Modifier = Modifier,
    state: HourlyForecastWeatherUiState
) {

}
