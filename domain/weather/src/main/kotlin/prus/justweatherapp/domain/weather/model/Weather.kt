package prus.justweatherapp.domain.weather.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import prus.justweatherapp.domain.weather.model.scale.PressureScale
import prus.justweatherapp.domain.weather.model.scale.TempScale
import kotlin.time.Duration

data class Weather(
    val locationId: String,
    val dateTime: LocalDateTime,
    val timezoneOffset: Int,
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val tempScale: TempScale,
    val pressure: Double,
    val pressureScale: PressureScale,
    val humidity: Double,
    val weatherConditions: WeatherConditions,
    val clouds: Double? = null,
    val rain: Double? = null,
    val snow: Double? = null,
    val wind: Wind? = null,
    val visibility: Int? = null,
    val probOfPrecipitations: Double? = null,
    val sunrise: LocalTime,
    val sunset: LocalTime,
    val daylight: Duration,
)
