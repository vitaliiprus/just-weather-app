package prus.justweatherapp.remote.model.onecall

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TempDTO(
    @SerialName("day") val day: Double,
    @SerialName("night") val night: Double,
    @SerialName("morn") val morning: Double,
    @SerialName("eve") val evening: Double,
    @SerialName("min") val min: Double?,
    @SerialName("max") val max: Double?
)
