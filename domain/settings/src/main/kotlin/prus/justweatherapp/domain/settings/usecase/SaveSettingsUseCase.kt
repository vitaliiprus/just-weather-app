package prus.justweatherapp.domain.settings.usecase

import prus.justweatherapp.domain.settings.model.SettingsModel
import prus.justweatherapp.domain.settings.repository.SettingsRepository
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(
        settings: SettingsModel
    ) {
        return settingsRepository.saveSettings(settings)
    }
}