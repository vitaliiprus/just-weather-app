package prus.justweatherapp.remote.openweather.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.remote.serializer.LocalDateTimeAsLong

@Serializable
data class ForecastWeatherDataDTO(
    @SerialName("dt") val dateTime: LocalDateTimeAsLong,
    @SerialName("main") val main: MainWeatherDataDTO,
    @SerialName("weather") val weather: List<WeatherConditionsDTO>,
    @SerialName("clouds") val clouds: CloudsDTO? = null,
    @SerialName("rain") val rain: RainDTO? = null,
    @SerialName("snow") val snow: SnowDTO? = null,
    @SerialName("wind") val wind: WindDTO,
    @SerialName("visibility") val visibility: Int? = null,
    @SerialName("pop") val probOfPrecipitations: Double? = null,
)
