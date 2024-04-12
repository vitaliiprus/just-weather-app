package prus.justweatherapp.app.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import prus.justweatherapp.data.settings.repository.SettingsRepositoryImpl
import prus.justweatherapp.domain.settings.repository.SettingsRepository

@Module
@InstallIn(SingletonComponent::class)
interface SettingsDataModule {

    @Binds
    fun bindsSettingsRepository(
        settingsRepository: SettingsRepositoryImpl
    ): SettingsRepository

}