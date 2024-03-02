package prus.justweatherapp.data.weather.mapper

import prus.justweatherapp.domain.weather.model.CurrentWeather
import prus.justweatherapp.domain.weather.model.WeatherConditions
import prus.justweatherapp.remote.model.WeatherConditionsDTO
import prus.justweatherapp.remote.model.WeatherDTO

internal fun WeatherDTO.mapToDomainModel(locationId: String) =
    CurrentWeather(
        locationId = locationId,
        timezoneOffset = this.timezoneOffset,
        currentTemp = this.main.temp,
        minTemp = this.main.tempMin,
        maxTemp = this.main.tempMax,
        weatherConditions = getWeatherConditions(this.weather.first())
    )

fun getWeatherConditions(dto: WeatherConditionsDTO): WeatherConditions {
    return when (dto.id) {
        in (200..299) -> WeatherConditions.Thunderstorm
        in (300..399) -> WeatherConditions.ChanceRain
        in (500..599) -> WeatherConditions.Rain
        in (600..699) -> WeatherConditions.Snow
        in (700..799) -> WeatherConditions.Fog
        800 -> WeatherConditions.Clear
        801 -> WeatherConditions.MostlySunny
        802, 803 -> WeatherConditions.MostlyCloudy
        804 -> WeatherConditions.Cloudy
        else -> WeatherConditions.Unknown
    }
}
