package prus.justweatherapp.app.di.local

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import prus.justweatherapp.local.db.AppDatabase
import prus.justweatherapp.local.db.dao.LocationsDao
import prus.justweatherapp.local.db.dao.SunDataDao
import prus.justweatherapp.local.db.dao.UserLocationsDao
import prus.justweatherapp.local.db.dao.WeatherDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideLocationsDao(appDatabase: AppDatabase): LocationsDao {
        return appDatabase.locationsDao()
    }

    @Singleton
    @Provides
    fun provideUserLocationsDao(appDatabase: AppDatabase): UserLocationsDao {
        return appDatabase.userLocationsDao()
    }

    @Singleton
    @Provides
    fun provideWeatherDao(appDatabase: AppDatabase): WeatherDao {
        return appDatabase.weatherDao()
    }

    @Singleton
    @Provides
    fun provideSunDataDao(appDatabase: AppDatabase): SunDataDao {
        return appDatabase.sunDataDao()
    }
}