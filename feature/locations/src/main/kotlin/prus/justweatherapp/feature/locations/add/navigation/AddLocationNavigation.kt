package prus.justweatherapp.feature.locations.add.navigation

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import prus.justweatherapp.feature.locations.add.AddLocationRoute
import java.net.URLDecoder
import java.net.URLEncoder

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@VisibleForTesting
internal const val LOCATION_ID_ARG = "locationId"

internal class AddLocationArgs(val locationId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(
                URLDecoder.decode(
                    checkNotNull(savedStateHandle[LOCATION_ID_ARG]),
                    URL_CHARACTER_ENCODING
                )
            )
}

fun NavController.navigateToAddLocation(locationId: String) {
    val encodedId = URLEncoder.encode(locationId, URL_CHARACTER_ENCODING)
    navigate("add_location_route/$encodedId") {
        launchSingleTop = true
    }
}

fun NavGraphBuilder.addLocationScreen(
    onBackClicked: () -> Unit,
    onLocationAdded: () -> Unit,
) {
    composable(
        route = "add_location_route/{$LOCATION_ID_ARG}",
        arguments = listOf(
            navArgument(LOCATION_ID_ARG) { type = NavType.StringType },
        ),
    ) {
        AddLocationRoute(
            onBackClicked = onBackClicked,
            onLocationAdded = onLocationAdded
        )
    }
}