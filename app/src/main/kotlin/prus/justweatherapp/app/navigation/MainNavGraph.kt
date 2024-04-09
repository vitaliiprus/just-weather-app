package prus.justweatherapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import prus.justweatherapp.app.MAIN_ROUTE
import prus.justweatherapp.feature.locations.LocationsScreen
import prus.justweatherapp.feature.locations.add.navigation.addLocationScreen
import prus.justweatherapp.feature.locations.add.navigation.navigateToAddLocation
import prus.justweatherapp.feature.settings.SettingsUI
import prus.justweatherapp.feature.weather.navigation.navigateToLocationWeather
import prus.justweatherapp.feature.weather.navigation.weatherScreen

@Composable
fun MainNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = MAIN_ROUTE,
        startDestination = MainScreen.Weather.route
    ) {
        composable(route = MainScreen.MyLocations.route) {
            LocationsScreen(
                onSearchLocationClicked = navController::navigateToAddLocation,
                onLocationClicked = navController::navigateToLocationWeather
            )
        }

        weatherScreen(
            onFindLocationsClick = {
                navController.navigate(MainScreen.MyLocations.route) {
                    launchSingleTop = true
                }
            }
        )

        composable(route = MainScreen.Settings.route) {
            SettingsUI()
        }

        addLocationScreen(
            onBackClicked = navController::popBackStack,
            onLocationAdded = {
                navController.popBackStack(
                    route = MainScreen.MyLocations.route,
                    inclusive = false
                )
            }
        )
    }
}