package prus.justweatherapp.local.db.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WindDBO(
    @SerialName("speed") val speed: Double? = null,
    @SerialName("deg") val degree: Double? = null,
    @SerialName("gust") val gust: Double? = null,
)