package prus.justweatherapp.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RainDTO(
    @SerialName("1h") val h1: Double? = null,
    @SerialName("3h") val h3: Double? = null,
)
