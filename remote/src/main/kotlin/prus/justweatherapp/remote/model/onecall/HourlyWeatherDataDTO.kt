package prus.justweatherapp.remote.model.onecall

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.remote.model.WeatherConditionsDTO

@Serializable
data class HourlyWeatherDataDTO(
    @SerialName("dt") val dateTime: LocalDateTime,
    @SerialName("temp") val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    @SerialName("pressure") val pressure: Double,
    @SerialName("humidity") val humidity: Double,
    @SerialName("dew_point") val dewPoint: Double,
    @SerialName("uvi") val uvi: Double,
    @SerialName("clouds") val clouds: Double,
    @SerialName("visibility") val visibility: Double,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_deg") val windDeg: Double,
    @SerialName("wind_gust") val windGust: Double,
    @SerialName("weather") val weather: List<WeatherConditionsDTO>,
    @SerialName("pop") val probOfPrecipitation: Double,
)
