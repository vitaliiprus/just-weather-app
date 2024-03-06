package prus.justweatherapp.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RainDTO(
    @SerialName("3h") val h3: Double?,
)
