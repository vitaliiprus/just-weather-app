package prus.justweatherapp.feature.weather

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
) : ViewModel() {

    var state by mutableStateOf(
        WeatherState(
            screenState = ScreenState.Success
        )
    )
}