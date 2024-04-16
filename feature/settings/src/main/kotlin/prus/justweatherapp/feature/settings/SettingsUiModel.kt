package prus.justweatherapp.feature.settings

import prus.justweatherapp.core.ui.UiText

data class SettingsUiModel(
    val tempValue: UiText,
    val pressureValue: UiText,
    val windValue: UiText,
    val languageValue: UiText,
    val themeValue: UiText,
    val menuOptions: MenuOptionsUiModel
)

data class MenuOptionsUiModel(
    val tempOptions: List<MenuOption>,
    val pressureOptions: List<MenuOption>,
    val windOptions: List<MenuOption>,
    val languageOptions: List<MenuOption>,
    val themeOptions: List<MenuOption>,
)

data class MenuOption(
    val value: UiText,
    val selected: Boolean
)
