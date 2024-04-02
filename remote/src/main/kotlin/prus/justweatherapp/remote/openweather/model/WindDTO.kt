package prus.justweatherapp.remote.openweather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WindDTO(
    @SerialName("speed") val speed: Double?,
    @SerialName("deg") val degree: Double?,
    @SerialName("gust") val gust: Double? = null,
)
