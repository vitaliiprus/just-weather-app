package prus.justweatherapp.remote.model

data class ForecastResponseDTO(
    val timezoneOffset: Int,
    val hourly: List<WeatherDTO>,
    val sun: List<SunDataDTO>,
)
