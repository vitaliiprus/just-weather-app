package prus.justweatherapp.feature.locations.add

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import prus.justweatherapp.core.ui.components.JwaTextButton
import prus.justweatherapp.feature.locations.R
import prus.justweatherapp.feature.locations.model.LocationUiModel
import prus.justweatherapp.feature.weather.location.LocationWeatherUI
import prus.justweatherapp.theme.AppTheme
import prus.justweatherapp.theme.startEndPaddings
import prus.justweatherapp.theme.topBarSize

@Composable
internal fun AddLocationRoute(
    onBackClicked: () -> Unit,
    onLocationAdded: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AddLocationViewModel = hiltViewModel(),
) {
    val addLocationUiState by viewModel.state.collectAsStateWithLifecycle()

    if (addLocationUiState.isLocationAdded) {
        onLocationAdded()
    }

    AddLocationScreen(
        state = addLocationUiState,
        onBackClicked = onBackClicked,
        onAddLocationClicked = viewModel::onAddLocationClicked,
        modifier = modifier
    )
}

@Composable
private fun AddLocationScreen(
    state: AddLocationUiState,
    onBackClicked: () -> Unit,
    onAddLocationClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .topBarSize()
                .startEndPaddings(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            JwaTextButton(
                text = stringResource(id = R.string.cancel),
                color = MaterialTheme.colorScheme.onSurface,
                onClick = onBackClicked
            )

            when (state.locationDataState) {

                LocationDataState.Loading -> {
                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )
                }

                is LocationDataState.Error -> {

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                    )

                    Toast.makeText(
                        LocalContext.current,
                        state.locationDataState.message.asString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is LocationDataState.Success -> {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = state.locationDataState.location.name,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )

                    JwaTextButton(
                        text = stringResource(id = R.string.add),
                        onClick = onAddLocationClicked
                    )
                }
            }
        }

        if (state.locationDataState is LocationDataState.Success) {
            LocationWeatherUI(
                locationId = state.locationDataState.location.id
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun AddLocationScreenPreview() {
    AppTheme {
        Surface {
            AddLocationScreen(
                state = AddLocationUiState(
                    locationDataState = LocationDataState.Success(
                        location = LocationUiModel(
                            id = "",
                            name = "Saint Petersburg"
                        )
                    ),
                    weatherDataState = WeatherDataState.Loading,
                ),
                onBackClicked = {},
                onAddLocationClicked = {},
            )
        }
    }
}