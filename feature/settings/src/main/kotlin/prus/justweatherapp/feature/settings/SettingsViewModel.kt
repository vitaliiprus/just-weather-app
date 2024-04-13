package prus.justweatherapp.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.settings.model.scale.PressureScale
import prus.justweatherapp.domain.settings.model.scale.TempScale
import prus.justweatherapp.domain.settings.model.scale.WindScale
import prus.justweatherapp.domain.settings.usecase.GetSettingsUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {

    val state: StateFlow<SettingsUiState> =
        getSettingsUseCase()
            .asResult()
            .map { result ->
                result.getOrNull()?.let { model ->
                    SettingsUiState.Success(
                        settings = SettingsUiModel(
                            tempValue = getTempValue(model.tempScale),
                            pressureValue = getPressureValue(model.pressureScale),
                            windValue = getWindValue(model.windScale),

                            )
                    )
                } ?: SettingsUiState.Error(
                    result.exceptionOrNull()?.message ?: ""
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState.Loading
            )

    private fun getTempValue(tempScale: TempScale): UiText {
        return when (tempScale) {
            TempScale.KELVIN -> UiText.StringResource(R.string.scale_kelvin)
            TempScale.CELSIUS -> UiText.StringResource(R.string.scale_celsius)
            TempScale.FAHRENHEIT -> UiText.StringResource(R.string.scale_fahrenheit)
        }
    }

    private fun getPressureValue(pressureScale: PressureScale): UiText {
        return when (pressureScale) {
            PressureScale.MM_HG -> UiText.StringResource(R.string.scale_mm_hg)
            PressureScale.H_PA -> UiText.StringResource(R.string.scale_hpa)
        }
    }

    private fun getWindValue(windScale: WindScale): UiText {
        return when (windScale) {
            WindScale.M_S -> UiText.StringResource(R.string.scale_m_s)
            WindScale.KM_H -> UiText.StringResource(R.string.scale_km_h)
            WindScale.MPH -> UiText.StringResource(R.string.scale_mph)
            WindScale.KT -> UiText.StringResource(R.string.scale_kt)
        }
    }
}