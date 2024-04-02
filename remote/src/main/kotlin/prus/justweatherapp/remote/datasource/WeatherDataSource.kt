package prus.justweatherapp.remote.datasource

import prus.justweatherapp.remote.model.ForecastResponseDTO

interface WeatherDataSource {

    suspend fun getForecastWeatherData(lat: Double, lon: Double): Result<ForecastResponseDTO>
}