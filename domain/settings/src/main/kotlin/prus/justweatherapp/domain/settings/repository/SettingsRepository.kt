package prus.justweatherapp.domain.settings.repository

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.settings.model.SettingsModel

interface SettingsRepository {

    fun getSettings(): Flow<SettingsModel>

    suspend fun saveSettings(settings: SettingsModel)
}