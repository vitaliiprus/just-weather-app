package prus.justweatherapp.local.db.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Wind(
    @SerialName("speed") val speed: Double?,
    @SerialName("deg") val degree: Double?,
    @SerialName("gust") val gust: Double? = null,
)