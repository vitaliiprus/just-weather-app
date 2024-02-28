package prus.justweatherapp.remote.model.onecall

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MinutelyWeatherDataDTO(
    @SerialName("dt") val dateTime: LocalDateTime,
    @SerialName("precipitation") val precipitation: Double,
)
