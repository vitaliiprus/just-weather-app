package prus.justweatherapp.remote.openmeteo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.remote.serializer.LocalDateTimeAsLong

@Serializable
data class DailyWeatherDTO(
    @SerialName("time") val date: List<LocalDateTimeAsLong>,
    @SerialName("sunrise") val sunrise: List<LocalDateTimeAsLong>,
    @SerialName("sunset") val sunset: List<LocalDateTimeAsLong>,
    @SerialName("daylight_duration") val daylightDuration: List<Double>,
    @SerialName("sunshine_duration") val sunshineDuration: List<Double>,
)
