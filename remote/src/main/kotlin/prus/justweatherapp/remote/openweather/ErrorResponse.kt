package prus.justweatherapp.remote.openweather

import kotlinx.serialization.SerialName

data class ErrorResponse(
    @SerialName("cod") val code: Int,
    @SerialName("message") val message: String,
    @SerialName("parameters") val parameters: List<String>,
)
