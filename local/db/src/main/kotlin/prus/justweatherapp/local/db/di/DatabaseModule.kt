package prus.justweatherapp.local.db.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import prus.justweatherapp.local.db.AppDatabase
import prus.justweatherapp.local.db.dao.LocationsDao
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
    fun provideLocationDao(appDatabase: AppDatabase): LocationsDao {
        return appDatabase.locationsDao()
    }
}