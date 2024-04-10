package prus.justweatherapp.data.settings.repository

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.settings.model.SettingsModel
import prus.justweatherapp.domain.settings.repository.SettingsRepository

class SettingsRepositoryImpl : SettingsRepository {
    override fun getSettings(): Flow<SettingsModel> {
        TODO("Not yet implemented")
    }

    override suspend fun saveSettings(settings: SettingsModel) {
        TODO("Not yet implemented")
    }
}