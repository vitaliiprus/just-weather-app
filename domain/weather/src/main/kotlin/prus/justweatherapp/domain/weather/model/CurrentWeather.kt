package prus.justweatherapp.domain.weather.model

import kotlinx.datetime.LocalDateTime

data class CurrentWeather(
    val locationId: String,
    val time: LocalDateTime,
    val currentTemp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val weatherConditions: WeatherConditions,
)
