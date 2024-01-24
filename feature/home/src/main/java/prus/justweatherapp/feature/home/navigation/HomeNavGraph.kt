package prus.justweatherapp.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import prus.justweatherapp.core.presentation.navigation.Graph

fun NavGraphBuilder.homeNavGraph(
    navController: NavController
) {
    navigation(
        route = Graph.HOME,
        startDestination = HomeScreen.Screen1.route
    ) {
        composable(route = HomeScreen.Screen1.route) {
//            Screen1UI {}
        }
        composable(route = HomeScreen.Screen2.route) {
//            Screen2UI {}
        }
        composable(route = HomeScreen.Screen3.route) {
//            Screen3UI {}
        }
    }
}

sealed class HomeScreen(val route: String) {
    data object Screen1 : HomeScreen(route = SCREEN_1)
    data object Screen2 : HomeScreen(route = SCREEN_2)
    data object Screen3 : HomeScreen(route = SCREEN_3)

    companion object {

        const val SCREEN_1 = "screen_1"
        const val SCREEN_2 = "screen_2"
        const val SCREEN_3 = "screen_3"
    }
}