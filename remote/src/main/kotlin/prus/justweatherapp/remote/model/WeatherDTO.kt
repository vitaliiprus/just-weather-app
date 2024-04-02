package prus.justweatherapp.remote.model

import prus.justweatherapp.remote.serializer.LocalDateTimeAsLong

data class WeatherDTO(
    val dateTime: LocalDateTimeAsLong,
    val temp: Double,
    val feelsLike: Double,
    val humidity: Double,
    val pop: Double,
    val rain: Double,
    val showers: Double,
    val snowfall: Double,
    val weatherCode: Int,
    val pressure: Double,
    val cloudCover: Double,
    val visibility: Double,
    val windSpeed: Double,
    val windDirection: Double,
    val windGusts: Double,
    val uvi: Double,
)
