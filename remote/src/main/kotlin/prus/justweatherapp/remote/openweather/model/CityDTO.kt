package prus.justweatherapp.remote.openweather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.remote.serializer.LocalDateTimeAsLong

@Serializable
data class CityDTO(
    @SerialName("sunrise") val sunrise: LocalDateTimeAsLong,
    @SerialName("sunset") val sunset: LocalDateTimeAsLong,
    @SerialName("timezone") val timezoneOffset: Int,
)
