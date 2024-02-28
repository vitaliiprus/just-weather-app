package prus.justweatherapp.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import prus.justweatherapp.remote.BuildConfig
import prus.justweatherapp.remote.openweather.OpenWeatherApiClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Provides
    @Singleton
    fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun providesOkHttpCallFactory(): Call.Factory = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .apply {
                    if (BuildConfig.DEBUG) {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    }
                },
        )
        .build()


    @Provides
    fun providesOpenWeatherApiClient(
        networkJson: Json,
        okhttpCallFactory: Call.Factory,
    ): OpenWeatherApiClient {
        return OpenWeatherApiClient(
            networkJson,
            okhttpCallFactory
        )
    }

}