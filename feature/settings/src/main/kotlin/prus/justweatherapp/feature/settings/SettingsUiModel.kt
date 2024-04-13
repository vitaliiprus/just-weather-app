package prus.justweatherapp.feature.settings

import prus.justweatherapp.core.ui.UiText

data class SettingsUiModel(
    val tempValue: UiText,
    val pressureValue: UiText,
    val windValue: UiText,
)
