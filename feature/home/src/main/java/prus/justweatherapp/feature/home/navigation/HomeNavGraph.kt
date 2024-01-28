package prus.justweatherapp.feature.home.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import prus.justweatherapp.core.presentation.navigation.Graph

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
//            Screen1UI {}
        }
        composable(route = HomeScreen.Weather.route) {
//            Screen2UI {}
        }
        composable(route = HomeScreen.Settings.route) {
//            Screen3UI {}
        }
    }
}