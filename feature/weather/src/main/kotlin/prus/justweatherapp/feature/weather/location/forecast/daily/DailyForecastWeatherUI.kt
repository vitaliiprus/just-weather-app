package prus.justweatherapp.feature.weather.location.forecast.daily

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import prus.justweatherapp.core.ui.R
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.core.ui.shimmer.ShimmerRectangle
import prus.justweatherapp.feature.weather.location.forecast.daily.temprange.TempRangeModel
import prus.justweatherapp.feature.weather.location.forecast.daily.temprange.TempRangeUI
import prus.justweatherapp.feature.weather.location.forecast.weathercard.WeatherCardUI
import prus.justweatherapp.theme.Dimens
import prus.justweatherapp.theme.JwaTheme

@Composable
fun DailyForecastWeatherUI(
    modifier: Modifier,
    locationId: String
) {
    val viewModel: DailyForecastWeatherViewModel = hiltViewModel<
            DailyForecastWeatherViewModel,
            DailyForecastWeatherViewModel.ViewModelFactory
            >(
        key = DailyForecastWeatherViewModel::class.qualifiedName.plus(" $locationId")
    ) { factory ->
        factory.create(locationId)
    }

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
    val context = LocalContext.current

    if (state is DailyForecastWeatherUiState.Error) {
        Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
    }
    val dataItems: List<DailyForecastWeatherUiModel>? =
        if (state is DailyForecastWeatherUiState.Success) state.weather
        else null

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                PaddingValues(
                    start = Dimens.contentPaddings.start,
                    end = Dimens.contentPaddings.end,
                    bottom = Dimens.contentPaddings.bottom,
                )
            )
            .then(modifier),
    ) {
        dataItems?.let {
            dataItems.forEach { item ->
                DailyWeatherItem(
                    data = item
                )
            }
        } ?: run {
            for (i in 0..4) {
                ShimmeringItem()
            }
        }
    }
}

private val itemPadding = PaddingValues(
    start = 8.dp,
    end = 8.dp,
    top = 6.dp,
    bottom = 6.dp
)

@Composable
private fun DailyWeatherItem(
    data: DailyForecastWeatherUiModel
) {
    Row(
        modifier = Modifier
            .padding(itemPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {

        WeatherCardUI(
            weatherConditionImageResId = data.conditionImageResId,
            weatherConditionString = data.weatherConditions.asString(),
            precipitationProb = data.precipitationProb
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            Text(
                text = data.date.asString(),
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold,
                color = if (data.isWeekend)
                    MaterialTheme.colorScheme.error
                else
                    MaterialTheme.colorScheme.onSurface,

                fontSize = 16.sp,
            )

            Text(
                text = data.weatherConditions.asString(),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )

        }

        Spacer(modifier = Modifier.weight(1f))

        TempRangeUI(
            modifier = Modifier
                .width(130.dp),
            data = data.tempRangeModel
        )

    }
}

@Composable
private fun ShimmeringItem() {
    Row(
        modifier = Modifier
            .padding(itemPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {

        ShimmerRectangle(
            modifier = Modifier
                .size(60.dp)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {

            ShimmerRectangle(
                modifier = Modifier
                    .width(80.dp)
                    .height(16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            ShimmerRectangle(
                modifier = Modifier
                    .width(60.dp)
                    .height(16.dp)
            )

        }

        Spacer(modifier = Modifier.weight(1f))

        ShimmerRectangle(
            modifier = Modifier
                .width(140.dp)
                .height(50.dp)
        )
    }
}

@PreviewLightDark
@Composable
private fun DailyForecastWeatherUISuccessPreview(
) {
    JwaTheme {
        Surface {
            DailyForecastWeatherUI(
                state = DailyForecastWeatherUiState.Success(
                    weather = listOf(
                        DailyForecastWeatherUiModel(
                            date = UiText.StringResource(prus.justweatherapp.feature.weather.R.string.today),
                            isWeekend = false,
                            weatherConditions = UiText.DynamicString("Mostly cloudy"),
                            conditionImageResId = R.drawable.mostlycloudy,
                            precipitationProb = "10%",
                            tempRangeModel = TempRangeModel(
                                dayMinTemp = -2.0,
                                dayMaxTemp = 9.0,
                                currentTemp = 6.0,
                                rangeMinTemp = -2.0,
                                rangeMaxTemp = 12.0
                            )
                        ),
                        DailyForecastWeatherUiModel(
                            date = UiText.StringResource(prus.justweatherapp.feature.weather.R.string.tomorrow),
                            isWeekend = false,
                            weatherConditions = UiText.DynamicString("Mostly sunny"),
                            conditionImageResId = R.drawable.mostlysunny,
                            precipitationProb = null,
                            tempRangeModel = TempRangeModel(
                                dayMinTemp = 1.0,
                                dayMaxTemp = 10.0,
                                rangeMinTemp = -2.0,
                                rangeMaxTemp = 12.0
                            )
                        ),
                        DailyForecastWeatherUiModel(
                            date = UiText.DynamicString("SA, 30 March"),
                            isWeekend = true,
                            weatherConditions = UiText.DynamicString("Chance rain"),
                            conditionImageResId = R.drawable.chancerain,
                            precipitationProb = "29%",
                            tempRangeModel = TempRangeModel(
                                dayMinTemp = 7.0,
                                dayMaxTemp = 12.0,
                                rangeMinTemp = -2.0,
                                rangeMaxTemp = 12.0
                            )
                        ),
                    )
                )
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun DailyForecastWeatherUILoadingPreview(
) {
    JwaTheme {
        Surface {
            DailyForecastWeatherUI(
                state = DailyForecastWeatherUiState.Loading
            )
        }
    }
}
