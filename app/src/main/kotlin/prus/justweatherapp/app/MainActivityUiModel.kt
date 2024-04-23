package prus.justweatherapp.app

import prus.justweatherapp.domain.settings.model.AppLanguage
import prus.justweatherapp.domain.settings.model.AppTheme

data class MainActivityUiModel(
    val appTheme: AppTheme,
    val appLanguage: AppLanguage
)