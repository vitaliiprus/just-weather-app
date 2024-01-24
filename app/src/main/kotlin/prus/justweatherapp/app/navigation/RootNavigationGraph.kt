package prus.justweatherapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import prus.justweatherapp.core.presentation.navigation.Graph
import prus.justweatherapp.feature.home.navigation.homeNavGraph

@Composable
fun RootNavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.HOME,
    ) {
//        authNavGraph(
//            navController = navController
//        )
        homeNavGraph(
            navController = navController
        )
    }
}