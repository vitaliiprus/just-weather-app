package prus.justweatherapp.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import prus.justweatherapp.core.dispatchers.Dispatcher

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @Provides
    @Dispatcher(prus.justweatherapp.core.dispatchers.Dispatchers.Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Dispatcher(prus.justweatherapp.core.dispatchers.Dispatchers.IO)
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(prus.justweatherapp.core.dispatchers.Dispatchers.Main)
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}