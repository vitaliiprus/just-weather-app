package prus.justweatherapp.remote.model

data class ForecastResponseDTO(
    val timezoneOffset: Int,
    val hourly: List<HourlyWeatherDTO>,
    val sun: List<SunDataDTO>,
)
