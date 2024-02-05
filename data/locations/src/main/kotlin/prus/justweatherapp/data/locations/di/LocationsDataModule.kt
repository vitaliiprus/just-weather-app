package prus.justweatherapp.data.locations.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import prus.justweatherapp.data.locations.repository.LocationsRepositoryImpl
import prus.justweatherapp.data.locations.repository.UserLocationsRepositoryImpl
import prus.justweatherapp.domain.locations.repository.LocationsRepository
import prus.justweatherapp.domain.locations.repository.UserLocationsRepository

@Module
@InstallIn(SingletonComponent::class)
interface LocationsDataModule {

    @Binds
    fun bindsLocationsRepository(
        locationsRepository: LocationsRepositoryImpl
    ): LocationsRepository

    @Binds
    fun bindsUserLocationsRepository(
        userLocationsRepository: UserLocationsRepositoryImpl
    ): UserLocationsRepository
}