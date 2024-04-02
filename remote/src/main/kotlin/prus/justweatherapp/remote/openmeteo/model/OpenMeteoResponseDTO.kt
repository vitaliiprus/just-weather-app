package prus.justweatherapp.remote.openmeteo.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenMeteoResponseDTO(
    @SerialName("utc_offset_seconds") val timezoneOffset: Int,
    @SerialName("hourly") val hourly: HourlyWeatherDTO,
    @SerialName("daily") val daily: DailyWeatherDTO,
)
