package prus.justweatherapp.feature.home.navigation

import android.content.res.Configuration
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import prus.justweatherapp.theme.AppTheme

@Composable
fun HomeBottomNavigation(
    navController: NavController,
    items: List<HomeScreen>,
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
                    selectedIconColor = MaterialTheme.colorScheme.tertiary,
                    selectedIndicatorColor = Color.Transparent,
                    selectedTextColor = MaterialTheme.colorScheme.tertiary
                ),
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
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
                        text = stringResource(screen.textResId)
                    )
                }
            )
        }
    }
}

@Preview(
    name = "LightTheme",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "DarkTheme",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun HomeBottomNavigationPreview() {
    AppTheme {
        HomeBottomNavigation(
            navController = rememberNavController(),
            items = listOf(HomeScreen.MyLocations, HomeScreen.Weather, HomeScreen.Settings)
        )
    }
}