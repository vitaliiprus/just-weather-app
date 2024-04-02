package prus.justweatherapp.remote.openmeteo

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import prus.justweatherapp.remote.BuildConfig
import prus.justweatherapp.remote.datasource.WeatherDataSource
import prus.justweatherapp.remote.model.ForecastResponseDTO
import prus.justweatherapp.remote.openmeteo.api.OpenMeteoApi
import prus.justweatherapp.remote.openmeteo.mapper.mapToForecastResponseDto
import retrofit2.Retrofit

class OpenMeteoApiClient(
    networkJson: Json,
    okhttpCallFactory: Call.Factory,
) : WeatherDataSource {

    private val baseUrl = BuildConfig.OPENMETEO_BASE_URL

    private val networkApi = Retrofit.Builder()
        .baseUrl(baseUrl)
        .callFactory(okhttpCallFactory)
        .addConverterFactory(
            networkJson.asConverterFactory("application/json".toMediaType()),
        )
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .build()
        .create(OpenMeteoApi::class.java)

    override suspend fun getForecastWeatherData(lat: Double, lon: Double): Result<ForecastResponseDTO> {
        return networkApi.getForecastWeather(lat, lon).mapToForecastResponseDto()
    }
}