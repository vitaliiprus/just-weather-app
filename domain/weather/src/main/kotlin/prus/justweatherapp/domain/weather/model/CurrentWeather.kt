package prus.justweatherapp.domain.weather.model

data class CurrentWeather(
    val locationId: String,
    val timezoneOffset: Int,
    val currentTemp: Double,
    val minTemp: Double,
    val maxTemp: Double,
    val weatherConditions: WeatherConditions,
)
