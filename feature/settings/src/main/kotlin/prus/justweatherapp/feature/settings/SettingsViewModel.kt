package prus.justweatherapp.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import prus.justweatherapp.core.common.result.asResult
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.settings.model.AppLanguage
import prus.justweatherapp.domain.settings.model.AppTheme
import prus.justweatherapp.domain.settings.model.SettingsModel
import prus.justweatherapp.domain.settings.model.scale.PressureScale
import prus.justweatherapp.domain.settings.model.scale.TempScale
import prus.justweatherapp.domain.settings.model.scale.WindScale
import prus.justweatherapp.domain.settings.usecase.GetSettingsUseCase
import prus.justweatherapp.domain.settings.usecase.SaveSettingsUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getSettingsUseCase: GetSettingsUseCase,
    val saveSettingsUseCase: SaveSettingsUseCase
) : ViewModel() {

    private lateinit var settingsModel: SettingsModel

    val state: StateFlow<SettingsUiState> =
        getSettingsUseCase()
            .asResult()
            .map { result ->
                result.getOrNull()?.let { model ->
                    settingsModel = model
                    SettingsUiState.Success(
                        settings = SettingsUiModel(
                            tempValue = getTempValue(model.tempScale),
                            pressureValue = getPressureValue(model.pressureScale),
                            windValue = getWindValue(model.windScale),
                            languageValue = getLanguageValue(model.appLanguage),
                            themeValue = getThemeValue(model.appTheme),
                            menuOptions = getMenuOptions(model)
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


    val uiCallbacks = SettingsCallbacks(
        onTempChanged = {
            saveSettings(
                settingsModel.copy(
                    tempScale = TempScale.entries[it]
                )
            )
        },
        onPressureChanged = {
            saveSettings(
                settingsModel.copy(
                    pressureScale = PressureScale.entries[it]
                )
            )
        },
        onWindChanged = {
            saveSettings(
                settingsModel.copy(
                    windScale = WindScale.entries[it]
                )
            )
        },
        onLanguageChanged = {
            saveSettings(
                settingsModel.copy(
                    appLanguage = AppLanguage.entries[it]
                )
            )
        },
        onThemeChanged = {
            saveSettings(
                settingsModel.copy(
                    appTheme = AppTheme.entries[it]
                )
            )
        },
    )

    private fun saveSettings(settingsModel: SettingsModel) {
        viewModelScope.launch {
            saveSettingsUseCase(settingsModel)
        }
    }

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

    private fun getLanguageValue(appLanguage: AppLanguage): UiText {
        return when (appLanguage) {
            AppLanguage.ENGLISH -> UiText.StringResource(R.string.language_english)
            AppLanguage.FINNISH -> UiText.StringResource(R.string.language_finnish)
            AppLanguage.RUSSIAN -> UiText.StringResource(R.string.language_russian)
        }
    }

    private fun getThemeValue(appTheme: AppTheme): UiText {
        return when (appTheme) {
            AppTheme.LIGHT -> UiText.StringResource(R.string.theme_light)
            AppTheme.DARK -> UiText.StringResource(R.string.theme_dark)
            AppTheme.SYSTEM -> UiText.StringResource(R.string.theme_system)
        }
    }

    private fun getMenuOptions(model: SettingsModel): MenuOptionsUiModel {
        return MenuOptionsUiModel(
            tempOptions = TempScale.entries.map {
                MenuOption(
                    value = getTempValue(it),
                    selected = model.tempScale == it
                )
            },
            pressureOptions = PressureScale.entries.map {
                MenuOption(
                    value = getPressureValue(it),
                    selected = model.pressureScale == it
                )
            },
            windOptions = WindScale.entries.map {
                MenuOption(
                    value = getWindValue(it),
                    selected = model.windScale == it
                )
            },
            languageOptions = AppLanguage.entries.map {
                MenuOption(
                    value = getLanguageValue(it),
                    selected = model.appLanguage == it
                )
            },
            themeOptions = AppTheme.entries.map {
                MenuOption(
                    value = getThemeValue(it),
                    selected = model.appTheme == it
                )
            },
        )
    }
}