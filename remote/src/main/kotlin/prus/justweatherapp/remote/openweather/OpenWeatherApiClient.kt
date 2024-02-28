package prus.justweatherapp.remote.openweather

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import prus.justweatherapp.remote.BuildConfig
import prus.justweatherapp.remote.datasource.WeatherDataSource
import prus.justweatherapp.remote.model.WeatherDTO
import prus.justweatherapp.remote.openweather.api.OpenWeatherApi
import retrofit2.Retrofit

class OpenWeatherApiClient(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : WeatherDataSource {

    private val baseUrl = BuildConfig.OPENWEATHER_BASE_URL
    private val apiKey = BuildConfig.OPENWEATHER_API_KEY

    private val networkApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .build()
        .create(OpenWeatherApi::class.java)

    override suspend fun getWeatherData(lat: Double, lon: Double): WeatherDTO {
        return networkApi.getCurrentWeather(lat, lon, apiKey)
    }
}