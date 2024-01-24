package prus.justweatherapp.feature.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject internal constructor(
) : ViewModel() {

    var state by mutableStateOf(
        HomeState(
            screenState = ScreenState.Loading
        )
    )
}