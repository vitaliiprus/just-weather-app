package prus.justweatherapp.core.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import prus.justweatherapp.core.common.util.LocaleChangeListener
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonUtilsModule {

    @Singleton
    @Provides
    fun provideLocaleChangeListener(@ApplicationContext context: Context): LocaleChangeListener {
        return LocaleChangeListener(context)
    }
}