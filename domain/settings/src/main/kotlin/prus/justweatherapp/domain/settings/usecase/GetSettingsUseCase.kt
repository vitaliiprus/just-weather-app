package prus.justweatherapp.domain.settings.usecase

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.settings.model.SettingsModel
import prus.justweatherapp.domain.settings.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(
    ): Flow<SettingsModel> {
        return settingsRepository.getSettings()
    }
}