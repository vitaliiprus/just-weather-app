package prus.justweatherapp.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoordinatesDTO(
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
)
