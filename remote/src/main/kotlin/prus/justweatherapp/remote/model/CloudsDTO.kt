package prus.justweatherapp.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CloudsDTO(
    @SerialName("all") val all: Double
)
