package prus.justweatherapp.app.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import prus.justweatherapp.theme.JwaTheme
import prus.justweatherapp.theme.accent

@Composable
fun MainBottomNavigation(
    navController: NavController,
    items: List<MainScreen>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach { screen ->
            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
            NavigationBarItem(
                selected = selected,
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIconColor = accent,
                    selectedIndicatorColor = Color.Transparent,
                    selectedTextColor = accent,
                    unselectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.primary
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = stringResource(
                            id = screen.textResId
                        )
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.textResId),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            )
        }
    }
}

@PreviewLightDark
@Composable
fun MainBottomNavigationPreview() {
    JwaTheme {
        MainBottomNavigation(
            navController = rememberNavController(),
            items = listOf(MainScreen.MyLocations, MainScreen.Weather, MainScreen.Settings)
        )
    }
}