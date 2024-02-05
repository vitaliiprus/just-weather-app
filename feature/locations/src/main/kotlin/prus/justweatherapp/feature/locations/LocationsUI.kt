package prus.justweatherapp.feature.locations

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun LocationsUI() {
    val viewModel: LocationsViewModel = hiltViewModel()
    val state = viewModel.state.screenState

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    )
    {
        val context = LocalContext.current

        when (state) {
            is ScreenState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
            }

            is ScreenState.Loading -> {
//                ScreenProgress()
            }

            ScreenState.Success -> {
                Text(
                    text = "My locations"
                )
            }
        }
    }
}