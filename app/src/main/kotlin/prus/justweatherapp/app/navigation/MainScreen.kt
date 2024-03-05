package prus.justweatherapp.app.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import prus.justweatherapp.app.R

sealed class MainScreen(
    val route: String,
    @StringRes val textResId: Int,
    @DrawableRes val icon: Int
) {
    data object MyLocations : MainScreen(
        route = MY_LOCATIONS,
        textResId = R.string.my_locations,
        icon = R.drawable.icon_location
    )
    data object Weather : MainScreen(
        route = WEATHER,
        textResId = R.string.weather,
        icon = R.drawable.icon_weather
    )
    data object Settings : MainScreen(
        route = SETTINGS,
        textResId = R.string.settings,
        icon = R.drawable.icon_settings
    )

    companion object {
        const val MY_LOCATIONS = "my_locations"
        const val WEATHER = "weather"
        const val SETTINGS = "settings"
    }
}