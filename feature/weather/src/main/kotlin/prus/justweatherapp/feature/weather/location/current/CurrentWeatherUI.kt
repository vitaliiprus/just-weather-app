package prus.justweatherapp.feature.weather.location.current

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.core.ui.components.JwaLabeledText
import prus.justweatherapp.core.ui.shimmer.ShimmerRectangle
import prus.justweatherapp.feature.weather.R
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.contentPaddings

@Composable
fun CurrentWeatherUI(
    modifier: Modifier,
    locationId: String,
    viewModel: CurrentWeatherViewModel = hiltViewModel<
            CurrentWeatherViewModel,
            CurrentWeatherViewModel.ViewModelFactory
            >(key = locationId) { factory ->
        factory.create(locationId)
    }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val timeState by viewModel.timeState.collectAsStateWithLifecycle()

    CurrentWeatherUI(
        modifier = modifier,
        state = state,
        timeState = timeState,
    )
}

@Composable
private fun CurrentWeatherUI(
    modifier: Modifier = Modifier,
    state: CurrentWeatherUiState,
    timeState: CurrentWeatherTimeUiState
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(350.dp),
        shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.onTertiary
        ),
    )
    {
        val context = LocalContext.current

        if (state is CurrentWeatherUiState.Error) {
            Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
        }

        val weather: CurrentWeatherUiModel? =
            if (state is CurrentWeatherUiState.Success) state.weather
            else null

        val time: String? =
            if (timeState is CurrentWeatherTimeUiState.Success) timeState.time
            else null

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .contentPaddings(),
        ) {

            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    if (time != null) {
                        Text(
                            text = time,
                            style = MaterialTheme.typography.bodySmall,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    } else {
                        ShimmerRectangle(
                            modifier = Modifier
                                .width(140.dp)
                                .height(16.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                    if (weather != null) {
                        Text(
                            text = weather.temp,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 56.sp,
                        )
                        Text(
                            text = weather.weatherConditions.asString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                        Text(
                            text = weather.feelsLike.asString(),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    } else {

                        ShimmerRectangle(
                            modifier = Modifier
                                .width(130.dp)
                                .height(70.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ShimmerRectangle(
                            modifier = Modifier
                                .width(90.dp)
                                .height(18.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        ShimmerRectangle(
                            modifier = Modifier
                                .width(120.dp)
                                .height(18.dp)
                        )
                    }
                }

                if (weather != null) {
                    Image(
                        modifier = Modifier
                            .size(130.dp),
                        painter = painterResource(id = weather.conditionImageResId),
                        contentDescription = weather.weatherConditions.asString()
                    )
                } else {
                    ShimmerRectangle(
                        modifier = Modifier
                            .size(130.dp)
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (weather != null) {
                    JwaLabeledText(
                        label = stringResource(id = R.string.sunrise),
                        text = weather.sunrise
                    )
                    JwaLabeledText(
                        label = stringResource(id = R.string.daylight),
                        text = weather.daylight
                    )
                    JwaLabeledText(
                        label = stringResource(id = R.string.sunset),
                        text = weather.sunset
                    )
                } else {
                    ShimmerRectangle(
                        modifier = Modifier
                            .width(70.dp)
                            .height(40.dp)
                    )
                    ShimmerRectangle(
                        modifier = Modifier
                            .width(70.dp)
                            .height(40.dp)
                    )
                    ShimmerRectangle(
                        modifier = Modifier
                            .width(70.dp)
                            .height(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (weather != null) {
                    JwaLabeledText(
                        label = stringResource(id = R.string.temp_min_max),
                        text = weather.tempMinMax
                    )
                    JwaLabeledText(
                        label = stringResource(id = R.string.uv_index),
                        text = weather.uvIndex
                    )
                    JwaLabeledText(
                        label = stringResource(id = R.string.pressure),
                        text = weather.pressure.asString()
                    )
                } else {
                    ShimmerRectangle(
                        modifier = Modifier
                            .width(70.dp)
                            .height(40.dp)
                    )
                    ShimmerRectangle(
                        modifier = Modifier
                            .width(70.dp)
                            .height(40.dp)
                    )
                    ShimmerRectangle(
                        modifier = Modifier
                            .width(70.dp)
                            .height(40.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                if (weather != null) {
                    JwaLabeledText(
                        label = stringResource(id = R.string.precip_prob),
                        text = weather.precipitationProb
                    )
                    JwaLabeledText(
                        label = stringResource(id = R.string.humidity),
                        text = weather.humidity
                    )
                    JwaLabeledText(
                        label = stringResource(id = R.string.wind),
                        text = weather.wind.asString()
                    )
                } else {
                    ShimmerRectangle(
                        modifier = Modifier
                            .width(70.dp)
                            .height(40.dp)
                    )
                    ShimmerRectangle(
                        modifier = Modifier
                            .width(70.dp)
                            .height(40.dp)
                    )
                    ShimmerRectangle(
                        modifier = Modifier
                            .width(70.dp)
                            .height(40.dp)
                    )
                }
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
                state = CurrentWeatherUiState.Loading,
                timeState = CurrentWeatherTimeUiState.Loading,
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
                    weather = CurrentWeatherUiModel(
                        temp = "15ºC",
                        feelsLike = UiText.StringResource(
                            id = R.string.template_feels_like,
                            args = arrayOf("14ºC")
                        ),
                        weatherConditions = UiText.DynamicString("Mostly cloudy"),
                        conditionImageResId = prus.justweatherapp.core.ui.R.drawable.mostlycloudy,
                        sunrise = "07:07",
                        daylight = "12:01",
                        sunset = "19:08",
                        tempMinMax = "↓6º ↑18º",
                        uvIndex = "1",
                        pressure = UiText.DynamicString("765 mmHg"),
                        precipitationProb = "5%",
                        humidity = "87%",
                        wind = UiText.DynamicString("2 m/s, S")
                    )
                ),
                timeState = CurrentWeatherTimeUiState.Success(
                    time = "Mon, 18 March, 15:40",
                )
            )
        }
    }
}