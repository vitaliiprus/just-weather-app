package prus.justweatherapp.app.di.data

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import prus.justweatherapp.data.weather.repository.WeatherRepositoryImpl
import prus.justweatherapp.domain.weather.repository.WeatherRepository

@Module
@InstallIn(SingletonComponent::class)
interface WeatherDataModule {

    @Binds
    fun bindsWeatherRepository(
        weatherRepository: WeatherRepositoryImpl
    ): WeatherRepository
}