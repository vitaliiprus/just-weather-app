package prus.justweatherapp.feature.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeUI () {
    val viewModel : HomeViewModel = hiltViewModel()
    val state = viewModel.state.screenState
}