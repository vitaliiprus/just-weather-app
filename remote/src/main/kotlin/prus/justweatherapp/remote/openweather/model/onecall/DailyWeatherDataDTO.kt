package prus.justweatherapp.remote.openweather.model.onecall

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.remote.openweather.model.WeatherConditionsDTO

@Serializable
data class DailyWeatherDataDTO(
    @SerialName("dt") val dateTime: LocalDateTime,
    @SerialName("sunrise") val sunrise: LocalDateTime,
    @SerialName("sunset") val sunset: LocalDateTime,
    @SerialName("moonrise") val moonrise: LocalDateTime,
    @SerialName("moonset") val moonset: LocalDateTime,
    @SerialName("moon_phase") val moonPhase: Double,
    @SerialName("summary") val summary: String,
    @SerialName("temp") val temp: TempDTO,
    @SerialName("feels_like") val feelsLike: TempDTO,
    @SerialName("pressure") val pressure: Double,
    @SerialName("humidity") val humidity: Double,
    @SerialName("dew_point") val dewPoint: Double,
    @SerialName("wind_speed") val windSpeed: Double,
    @SerialName("wind_deg") val windDeg: Double,
    @SerialName("wind_gust") val windGust: Double,
    @SerialName("weather") val weather: List<WeatherConditionsDTO>,
    @SerialName("clouds") val clouds: Double,
    @SerialName("uvi") val uvi: Double,
    @SerialName("pop") val probOfPrecipitation: Double,
    @SerialName("rain") val rainProb: Double,
)
