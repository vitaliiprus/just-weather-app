package prus.justweatherapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import prus.justweatherapp.feature.home.HOME_ROUTE
import prus.justweatherapp.feature.home.HomeUI

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HOME_ROUTE,
    ) {
        composable(
            route = HOME_ROUTE
        ) {
            HomeUI()
        }
    }
}