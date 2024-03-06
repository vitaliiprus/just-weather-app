package prus.justweatherapp.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.remote.serializer.LocalDateTimeAsLong

@Serializable
data class ForecastWeatherDataDTO(
    @SerialName("dt") val dateTime: LocalDateTimeAsLong,
    @SerialName("main") val main: MainWeatherDataDTO,
    @SerialName("weather") val weather: List<WeatherConditionsDTO>,
    @SerialName("clouds") val clouds: CloudsDTO?,
    @SerialName("rain") val rain: RainDTO?,
    @SerialName("snow") val snow: SnowDTO?,
    @SerialName("wind") val wind: WindDTO,
    @SerialName("visibility") val visibility: Int,
    @SerialName("pop") val probOfPrecipitations: Double,
)
