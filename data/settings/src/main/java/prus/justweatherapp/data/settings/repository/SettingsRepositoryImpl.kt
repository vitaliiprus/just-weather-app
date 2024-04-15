package prus.justweatherapp.data.settings.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import prus.justweatherapp.domain.settings.model.AppTheme
import prus.justweatherapp.domain.settings.model.AppLanguage
import prus.justweatherapp.domain.settings.model.SettingsModel
import prus.justweatherapp.domain.settings.model.scale.PressureScale
import prus.justweatherapp.domain.settings.model.scale.TempScale
import prus.justweatherapp.domain.settings.model.scale.WindScale
import prus.justweatherapp.domain.settings.repository.SettingsRepository
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    private object PreferencesKeys {
        val TEMP_SCALE = stringPreferencesKey("temp_scale")
        val PRESSURE_SCALE = stringPreferencesKey("pressure_scale")
        val WIND_SCALE = stringPreferencesKey("wind_scale")
        val APP_THEME = stringPreferencesKey("app_theme")
        val LANGUAGE = stringPreferencesKey("language")
    }

    private object DefaultValues {
        val TEMP_SCALE = TempScale.CELSIUS
        val PRESSURE_SCALE = PressureScale.MM_HG
        val WIND_SCALE = WindScale.M_S
        val APP_THEME = AppTheme.SYSTEM
        val AppLANGUAGE = AppLanguage.ENGLISH
    }

    override fun getSettings(): Flow<SettingsModel> =
        dataStore.data.map { prefs ->
            val tempScale = prefs[PreferencesKeys.TEMP_SCALE]?.let {
                TempScale.valueOf(it)
            } ?: DefaultValues.TEMP_SCALE
            val pressureScale = prefs[PreferencesKeys.PRESSURE_SCALE]?.let {
                PressureScale.valueOf(it)
            } ?: DefaultValues.PRESSURE_SCALE
            val windScale = prefs[PreferencesKeys.WIND_SCALE]?.let {
                WindScale.valueOf(it)
            } ?: DefaultValues.WIND_SCALE
            val appTheme = prefs[PreferencesKeys.APP_THEME]?.let {
                AppTheme.valueOf(it)
            } ?: DefaultValues.APP_THEME
            val appLanguage = prefs[PreferencesKeys.LANGUAGE]?.let {
                AppLanguage.valueOf(it)
            } ?: DefaultValues.AppLANGUAGE

            SettingsModel(
                tempScale = tempScale,
                pressureScale = pressureScale,
                windScale = windScale,
                appTheme = appTheme,
                appLanguage = appLanguage
            )
        }

    override suspend fun saveSettings(settings: SettingsModel) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.TEMP_SCALE] = settings.tempScale.name
            prefs[PreferencesKeys.PRESSURE_SCALE] = settings.pressureScale.name
            prefs[PreferencesKeys.WIND_SCALE] = settings.windScale.name
            prefs[PreferencesKeys.APP_THEME] = settings.appTheme.name
            prefs[PreferencesKeys.LANGUAGE] = settings.appLanguage.name
        }
    }
}