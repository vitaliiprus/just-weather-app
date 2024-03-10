package prus.justweatherapp.domain.weather.model

import kotlinx.datetime.LocalDateTime

data class ForecastWeather(
    val locationId: String,
    val dateTime: LocalDateTime,
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Double,
    val humidity: Double,
    val weather: WeatherConditions? = null,
    val clouds: Double? = null,
    val rain: Double? = null,
    val snow: Double? = null,
    val wind: Wind? = null,
    val visibility: Int? = null,
    val probOfPrecipitations: Double? = null,
)
