package prus.justweatherapp.feature.home.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import prus.justweatherapp.feature.home.R

sealed class HomeScreen(
    val route: String,
    @StringRes val textResId: Int,
    @DrawableRes val icon: Int
) {
    data object MyLocations : HomeScreen(
        route = MY_LOCATIONS,
        textResId = R.string.my_locations,
        icon = R.drawable.icon_location
    )
    data object Weather : HomeScreen(
        route = WEATHER,
        textResId = R.string.weather,
        icon = R.drawable.icon_weather
    )
    data object Settings : HomeScreen(
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