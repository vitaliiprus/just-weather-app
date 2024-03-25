package prus.justweatherapp.feature.weather.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import prus.justweatherapp.feature.weather.location.current.CurrentWeatherUI
import prus.justweatherapp.feature.weather.location.forecast.hourly.HourlyForecastWeatherUI
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.Dimens
import prus.justweatherapp.theme.screenContentPaddings

@Composable
fun LocationWeatherUI(
    locationId: String,
    locationName: String? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (!locationName.isNullOrBlank()) {
            LocationName(
                locationName = locationName
            )
        }

        CurrentWeatherUI(
            modifier = Modifier
                .fillMaxWidth()
                .screenContentPaddings(),
            locationId = locationId
        )

        Spacer(modifier = Modifier.height(20.dp))

        HourlyForecastWeatherUI(
            modifier = Modifier.fillMaxWidth(),
            locationId = locationId
        )
//
//        Spacer(modifier = Modifier.height(20.dp))
//
//        DailyForecastWeatherUI(
//            modifier = Modifier.fillMaxWidth(),
//            locationId = locationId
//        )

    }
}

@Composable
private fun LocationName(
    locationName: String
) {
    Text(
        modifier = Modifier
            .padding(
                start = Dimens.contentPaddings.start + 10.dp,
                end = Dimens.contentPaddings.start,
                top = Dimens.contentPaddings.start
            ),
        text = locationName,
        style = MaterialTheme.typography.titleLarge
    )
}

@PreviewLightDark
@Composable
fun LocationNameText() {
    AppTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LocationName(
                locationName = "Saint Petersburg",
            )
        }
    }
}