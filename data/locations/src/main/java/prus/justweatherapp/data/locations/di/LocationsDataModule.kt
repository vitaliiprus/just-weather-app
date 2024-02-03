package prus.justweatherapp.data.locations.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import prus.justweatherapp.data.locations.repository.LocationsRepositoryImpl
import prus.justweatherapp.domain.locations.repository.LocationsRepository

@Module
@InstallIn(SingletonComponent::class)
interface LocationsDataModule {

    @Binds
    fun bindsLocationsRepository(
        locationsRepository: LocationsRepositoryImpl
    ): LocationsRepository
}