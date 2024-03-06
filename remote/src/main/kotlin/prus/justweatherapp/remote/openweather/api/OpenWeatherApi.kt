package prus.justweatherapp.remote.openweather.api

import prus.justweatherapp.remote.model.CurrentWeatherDTO
import prus.justweatherapp.remote.model.ForecastWeatherDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi {

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
    ): CurrentWeatherDTO

    @GET("forecast")
    suspend fun getForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
    ): ForecastWeatherDTO
}