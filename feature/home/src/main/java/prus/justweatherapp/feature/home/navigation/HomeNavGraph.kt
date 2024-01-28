package prus.justweatherapp.feature.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import prus.justweatherapp.core.presentation.navigation.Graph
import prus.justweatherapp.feature.locations.LocationsUI
import prus.justweatherapp.feature.settings.SettingsUI
import prus.justweatherapp.feature.weather.WeatherUI

@Composable
fun HomeNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = HomeScreen.Weather.route
    ) {
        composable(route = HomeScreen.MyLocations.route) {
            LocationsUI()
        }
        composable(route = HomeScreen.Weather.route) {
            WeatherUI()
        }
        composable(route = HomeScreen.Settings.route) {
            SettingsUI()
        }
    }
}