package prus.justweatherapp.feature.weather.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import prus.justweatherapp.feature.weather.WeatherUI
import java.net.URLDecoder
import java.net.URLEncoder

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
internal const val LOCATION_ID_ARG = "locationId"
const val WEATHER_ROUTE = "weather/{$LOCATION_ID_ARG}"

internal class WeatherLocationArgs(val locationId: String?) {
    companion object {
        fun getArgument(arg: String?): String? {
            return arg?.let {
                URLDecoder.decode(
                    arg,
                    URL_CHARACTER_ENCODING
                )
            }
        }
    }

    constructor(savedStateHandle: SavedStateHandle) :
            this(getArgument(savedStateHandle[LOCATION_ID_ARG]))
}

fun NavController.navigateToLocationWeather(locationId: String) {
    val encodedId = URLEncoder.encode(locationId, URL_CHARACTER_ENCODING)
    navigate("weather/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.weatherScreen(
) {
    composable(
        route = WEATHER_ROUTE,
        arguments = listOf(
            navArgument(LOCATION_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        WeatherUI()
    }
}