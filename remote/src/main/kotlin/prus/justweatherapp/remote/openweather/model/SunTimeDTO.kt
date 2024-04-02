package prus.justweatherapp.remote.openweather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.remote.serializer.LocalDateTimeAsLong

@Serializable
data class SunTimeDTO(
    @SerialName("sunrise") val sunrise: LocalDateTimeAsLong,
    @SerialName("sunset") val sunset: LocalDateTimeAsLong,
)
