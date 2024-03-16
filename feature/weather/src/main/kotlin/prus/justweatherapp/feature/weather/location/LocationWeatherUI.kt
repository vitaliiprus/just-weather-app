package prus.justweatherapp.feature.weather.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import prus.justweatherapp.feature.weather.location.current.CurrentWeatherUI

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
    }
}