package prus.justweatherapp.feature.weather.location.forecast.hourly

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.core.ui.shimmer.ShimmerRectangle
import prus.justweatherapp.feature.weather.location.forecast.weathercard.WeatherCardUI
import prus.justweatherapp.theme.JwaTheme
import prus.justweatherapp.theme.Dimens

@Composable
fun HourlyForecastWeatherUI(
    modifier: Modifier,
    locationId: String
) {
    val viewModel: HourlyForecastWeatherViewModel = hiltViewModel<
            HourlyForecastWeatherViewModel,
            HourlyForecastWeatherViewModel.ViewModelFactory
            >(
        key = HourlyForecastWeatherViewModel::class.qualifiedName.plus(" $locationId")
    ) { factory ->
        factory.create(locationId)
    }

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
    val context = LocalContext.current

    if (state is HourlyForecastWeatherUiState.Error) {
        Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
    }
    val dataItems: List<HourlyForecastWeatherUiModel>? =
        if (state is HourlyForecastWeatherUiState.Success) state.weather
        else null

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .then(modifier)
    ) {

        LazyRow(
            modifier = Modifier
                .fillMaxHeight(),
            contentPadding = PaddingValues(
                horizontal = Dimens.contentPaddings.start,
                vertical = 0.dp
            ),
            verticalAlignment = Alignment.Top
        ) {

            dataItems?.let { items ->
                items(
                    count = items.size
                ) {
                    HourlyWeatherItem(
                        data = items[it]
                    )
                }

            } ?: run {
                items(count = 10) {
                    ShimmeringItem()
                }
            }
        }

        GradientEdges()
    }
}

private val itemPadding = PaddingValues(
    start = 8.dp,
    end = 8.dp,
    top = 4.dp,
    bottom = 4.dp
)

@Composable
private fun HourlyWeatherItem(
    modifier: Modifier = Modifier,
    data: HourlyForecastWeatherUiModel
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(itemPadding)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {

        if (data.date != null) {
            Text(
                modifier = Modifier
                    .padding(
                        bottom = 4.dp,
                    ),
                text = data.date,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.labelLarge
            )
        }

        WeatherCardUI(
            weatherConditionImageResId = data.conditionImageResId,
            weatherConditionString = data.weatherConditions.asString(),
            precipitationProb = data.precipitationProb
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = data.time,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        )

        Text(
            text = data.temp,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
private fun ShimmeringItem() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(itemPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        ShimmerRectangle(
            modifier = Modifier
                .size(60.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        ShimmerRectangle(
            modifier = Modifier
                .width(40.dp)
                .height(12.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        ShimmerRectangle(
            modifier = Modifier
                .width(30.dp)
                .height(14.dp)
        )
    }
}

@Composable
private fun GradientEdges() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .width(16.dp)
                .fillMaxHeight()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surface.copy(0f),
                        )
                    )
                )
        )
        Box(
            modifier = Modifier
                .width(16.dp)
                .fillMaxHeight()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface.copy(0f),
                            MaterialTheme.colorScheme.surface,
                        )
                    )
                )
        )
    }
}

@PreviewLightDark
@Composable
private fun HourlyForecastWeatherUISuccessPreview(
) {
    JwaTheme {
        Surface {
            HourlyForecastWeatherUI(
                state = HourlyForecastWeatherUiState.Success(
                    weather = listOf(
                        HourlyForecastWeatherUiModel(
                            conditionImageResId = prus.justweatherapp.core.ui.R.drawable.rain,
                            weatherConditions = UiText.DynamicString("Chance rain"),
                            time = "11:00",
                            temp = "15º",
                            precipitationProb = "10%",
                            date = "01 Apr"
                        ),
                        HourlyForecastWeatherUiModel(
                            conditionImageResId = prus.justweatherapp.core.ui.R.drawable.mostlysunny,
                            weatherConditions = UiText.DynamicString("Mostly sunny"),
                            time = "12:00",
                            temp = "21º",
                        )
                    )
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun HourlyForecastWeatherUILoadingPreview(
) {
    JwaTheme {
        Surface {
            HourlyForecastWeatherUI(
                state = HourlyForecastWeatherUiState.Loading
            )
        }
    }
}