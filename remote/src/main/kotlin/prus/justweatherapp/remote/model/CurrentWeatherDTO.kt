package prus.justweatherapp.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.remote.serializer.LocalDateTimeAsLong

@Serializable
data class CurrentWeatherDTO(
    @SerialName("cod") val code: Int,
    @SerialName("coord") val coordinates: CoordinatesDTO,
    @SerialName("weather") val weather: List<WeatherConditionsDTO>,
    @SerialName("main") val main: MainWeatherDataDTO,
    @SerialName("visibility") val visibility: Int,
    @SerialName("wind") val wind: WindDTO,
    @SerialName("rain") val rain: RainDTO? = null,
    @SerialName("snow") val snow: SnowDTO? = null,
    @SerialName("clouds") val clouds: CloudsDTO? = null,
    @SerialName("dt") val dateTime: LocalDateTimeAsLong,
    @SerialName("sys") val sunTime: SunTimeDTO,
    @SerialName("timezone") val timezoneOffset: Int,
)
