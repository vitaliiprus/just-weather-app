package prus.justweatherapp.feature.weather.location.current

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import prus.justweatherapp.theme.AppTheme

@Composable
fun CurrentWeatherUI(
    modifier: Modifier,
    locationId: String,
    viewModel: CurrentWeatherViewModel = hiltViewModel<CurrentWeatherViewModel,
            CurrentWeatherViewModel.ViewModelFactory>
        (key = locationId) { factory ->
        factory.create(locationId)
    },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CurrentWeatherUI(
        modifier = modifier,
        state = state
    )
}

@Composable
fun CurrentWeatherUI(
    modifier: Modifier = Modifier,
    state: CurrentWeatherUiState
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(25.dp),
        colors = CardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            disabledContentColor = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.5f),
        )
    )
    {
        val context = LocalContext.current

        when (state) {
            is CurrentWeatherUiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }

            is CurrentWeatherUiState.Loading -> {
//                ScreenProgress()
            }

            is CurrentWeatherUiState.Success -> {
                Text(
                    text = "Weather"
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun CurrentWeatherUILoadingPreview(
) {
    AppTheme {
        Surface {
            CurrentWeatherUI(
                state = CurrentWeatherUiState.Loading
            )
        }
    }
}
@PreviewLightDark
@Composable
private fun CurrentWeatherUISuccessPreview(
) {
    AppTheme {
        Surface {
            CurrentWeatherUI(
                state = CurrentWeatherUiState.Success(
                    weather = WeatherUiModel(
                        temp = "15ºC",
                        feelsLike = "14ºC",
                        tempMinMax = "↓6º ↑18º"
                    )
                )
            )
        }
    }
}