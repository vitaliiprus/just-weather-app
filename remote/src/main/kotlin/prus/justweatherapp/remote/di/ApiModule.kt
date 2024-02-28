package prus.justweatherapp.remote.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import prus.justweatherapp.remote.datasource.WeatherDataSource
import prus.justweatherapp.remote.openweather.OpenWeatherApiClient

@Module
@InstallIn(SingletonComponent::class)
internal interface ApiModule {

    @Binds
    fun binds(impl: OpenWeatherApiClient): WeatherDataSource
}