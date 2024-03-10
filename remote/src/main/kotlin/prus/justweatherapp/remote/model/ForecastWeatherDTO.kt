package prus.justweatherapp.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastWeatherDTO(
    @SerialName("cod") val code: Int,
    @SerialName("list") val list: List<ForecastWeatherDataDTO>,
    @SerialName("city") val city: CityDTO,
)
