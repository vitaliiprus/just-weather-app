package prus.justweatherapp.feature.weather.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import prus.justweatherapp.feature.weather.location.current.CurrentWeatherUI
import prus.justweatherapp.feature.weather.location.forecast.daily.DailyForecastWeatherUI
import prus.justweatherapp.feature.weather.location.forecast.hourly.HourlyForecastWeatherUI

@Composable
fun LocationWeatherUI(
    locationId: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        CurrentWeatherUI(
            modifier = Modifier.fillMaxWidth(),
            locationId = locationId
        )

        Spacer(modifier = Modifier.height(20.dp))

        HourlyForecastWeatherUI(
            modifier = Modifier.fillMaxWidth(),
            locationId = locationId
        )

        Spacer(modifier = Modifier.height(20.dp))

        DailyForecastWeatherUI(
            modifier = Modifier.fillMaxWidth(),
            locationId = locationId
        )

    }
}