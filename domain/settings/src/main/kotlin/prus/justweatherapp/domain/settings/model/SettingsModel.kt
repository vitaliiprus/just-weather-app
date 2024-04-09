package prus.justweatherapp.domain.settings.model

import prus.justweatherapp.domain.settings.model.scale.PressureScale
import prus.justweatherapp.domain.settings.model.scale.TempScale
import prus.justweatherapp.domain.settings.model.scale.WindScale

data class SettingsModel(
    val tempScale: TempScale,
    val pressureScale: PressureScale,
    val windScale: WindScale,
    val appTheme: AppTheme,
)
