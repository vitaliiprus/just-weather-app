package prus.justweatherapp.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherConditionsDTO(
    @SerialName("id") val id: Int,
    @SerialName("main") val type: String,
    @SerialName("description") val description: String,
)
