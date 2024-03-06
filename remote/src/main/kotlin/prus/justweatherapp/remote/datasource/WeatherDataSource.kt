package prus.justweatherapp.remote.datasource

import prus.justweatherapp.remote.model.CurrentWeatherDTO
import prus.justweatherapp.remote.model.ForecastWeatherDTO

interface WeatherDataSource {

    suspend fun getCurrentWeatherData(lat: Double, lon: Double): CurrentWeatherDTO

    suspend fun getForecastWeatherData(lat: Double, lon: Double): ForecastWeatherDTO
}