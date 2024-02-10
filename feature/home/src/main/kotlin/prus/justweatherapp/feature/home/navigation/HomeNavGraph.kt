package prus.justweatherapp.feature.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import prus.justweatherapp.feature.home.HOME_ROUTE
import prus.justweatherapp.feature.locations.LocationsScreen
import prus.justweatherapp.feature.settings.SettingsUI
import prus.justweatherapp.feature.weather.WeatherUI

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = HOME_ROUTE,
        startDestination = HomeScreen.Weather.route
    ) {
        composable(route = HomeScreen.MyLocations.route) {
            LocationsScreen()
        }
        composable(route = HomeScreen.Weather.route) {
            WeatherUI()
        }
        composable(route = HomeScreen.Settings.route) {
            SettingsUI()
        }
    }
}