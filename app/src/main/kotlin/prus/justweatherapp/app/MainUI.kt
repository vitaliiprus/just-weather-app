package prus.justweatherapp.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import prus.justweatherapp.app.navigation.MainBottomNavigation
import prus.justweatherapp.app.navigation.MainNavGraph
import prus.justweatherapp.app.navigation.MainScreen

const val MAIN_ROUTE = "main_route"

@Composable
fun MainUI(
    navController: NavHostController = rememberNavController(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
        ) {
            MainNavGraph(
                navController = navController
            )
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(MainScreen.MyLocations, MainScreen.Weather, MainScreen.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        MainBottomNavigation(
            navController = navController,
            items = screens
        )
    }
}