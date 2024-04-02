package prus.justweatherapp.remote.model

import prus.justweatherapp.remote.serializer.LocalDateTimeAsLong

data class SunDataDTO(
    val dateTime: LocalDateTimeAsLong,
    val sunrise: LocalDateTimeAsLong,
    val sunset: LocalDateTimeAsLong,
    val daylightDuration: Double,
    val sunshineDuration: Double,
)
