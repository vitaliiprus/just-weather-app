package prus.justweatherapp.feature.home

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import prus.justweatherapp.feature.home.navigation.HomeBottomNavigation
import prus.justweatherapp.feature.home.navigation.HomeNavGraph
import prus.justweatherapp.feature.home.navigation.HomeScreen

@Composable
fun HomeUI(
    navController: NavHostController = rememberNavController(),
) {
//    val viewModel: HomeViewModel = hiltViewModel()
//    val state = viewModel.state.screenState

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) { padding ->
        HomeNavGraph(
            navController = navController
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(HomeScreen.MyLocations, HomeScreen.Weather, HomeScreen.Settings)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        HomeBottomNavigation(
            navController = navController,
            items = screens
        )
    }
}