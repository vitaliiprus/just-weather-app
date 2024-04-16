package prus.justweatherapp.feature.settings

data class SettingsCallbacks(
    val onTempChanged: (Int) -> Unit,
    val onWindChanged: (Int) -> Unit,
    val onPressureChanged: (Int) -> Unit,
    val onLanguageChanged: (Int) -> Unit,
    val onThemeChanged: (Int) -> Unit,
)
