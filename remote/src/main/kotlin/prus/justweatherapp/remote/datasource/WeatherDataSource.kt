package prus.justweatherapp.remote.datasource

import prus.justweatherapp.remote.model.WeatherDTO

interface WeatherDataSource {

    suspend fun getWeatherData(lat: Double, lon: Double): WeatherDTO
}