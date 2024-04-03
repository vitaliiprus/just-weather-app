package prus.justweatherapp.remote.openmeteo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.remote.serializer.LocalDateTimeAsLong

@Serializable
data class HourlyWeatherDTO(
    @SerialName("time") val dateTime: List<LocalDateTimeAsLong>,
    @SerialName("temperature_2m") val temp: List<Double>,
    @SerialName("apparent_temperature") val feelsLike: List<Double>,
    @SerialName("relative_humidity_2m") val humidity: List<Double>,
    @SerialName("precipitation_probability") val pop: List<Double?>,
    @SerialName("rain") val rain: List<Double?>,
    @SerialName("showers") val showers: List<Double?>,
    @SerialName("snowfall") val snowfall: List<Double?>,
    @SerialName("weather_code") val weatherCode: List<Int?>,
    @SerialName("surface_pressure") val pressure: List<Double>,
    @SerialName("cloud_cover") val cloudCover: List<Double?>,
    @SerialName("visibility") val visibility: List<Double?>,
    @SerialName("wind_speed_10m") val windSpeed: List<Double?>,
    @SerialName("wind_direction_10m") val windDirection: List<Double?>,
    @SerialName("wind_gusts_10m") val windGusts: List<Double?>,
    @SerialName("uv_index") val uvi: List<Double?>,
)
