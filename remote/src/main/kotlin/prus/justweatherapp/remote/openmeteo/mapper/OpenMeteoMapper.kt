package prus.justweatherapp.remote.openmeteo.mapper

import prus.justweatherapp.remote.model.ForecastResponseDTO
import prus.justweatherapp.remote.model.HourlyWeatherDTO
import prus.justweatherapp.remote.model.SunDataDTO
import prus.justweatherapp.remote.model.WeatherConditions
import prus.justweatherapp.remote.openmeteo.model.OpenMeteoResponseDTO

internal fun Result<OpenMeteoResponseDTO>.mapToForecastResponseDto() =
    this.map {
        ForecastResponseDTO(
            timezoneOffset = it.timezoneOffset,
            hourly = List(it.hourly.dateTime.size) { index ->
                HourlyWeatherDTO(
                    dateTime = it.hourly.dateTime[index],
                    temp = it.hourly.temp[index],
                    feelsLike = it.hourly.feelsLike[index],
                    humidity = it.hourly.humidity[index],
                    pop = it.hourly.pop[index],
                    rain = it.hourly.rain[index],
                    showers = it.hourly.showers[index],
                    snowfall = it.hourly.snowfall[index],
                    weatherConditions = mapToWeatherConditions(it.hourly.weatherCode[index]),
                    pressure = it.hourly.pressure[index],
                    cloudCover = it.hourly.cloudCover[index],
                    visibility = it.hourly.visibility[index],
                    windSpeed = it.hourly.windSpeed[index],
                    windDirection = it.hourly.windDirection[index],
                    windGusts = it.hourly.windGusts[index],
                    uvi = it.hourly.uvi[index],
                )
            },
            sun = List(it.daily.date.size) { index ->
                SunDataDTO(
                    dateTime = it.daily.date[index],
                    sunrise = it.daily.sunrise[index],
                    sunset = it.daily.sunset[index],
                    daylightDuration = it.daily.daylightDuration[index],
                    sunshineDuration = it.daily.sunshineDuration[index],
                )
            }
        )
    }

internal fun mapToWeatherConditions(weatherCode: Int?): WeatherConditions {
    return when (weatherCode) {
        0 -> WeatherConditions.Clear
        1 -> WeatherConditions.MostlySunny
        2 -> WeatherConditions.MostlyCloudy
        3 -> WeatherConditions.Cloudy
        in 40..49 -> WeatherConditions.Fog
        in 50..59 -> WeatherConditions.ChanceRain
        in 60..69 -> WeatherConditions.Rain
        in 70..79 -> WeatherConditions.Snow
        in 80..82 -> WeatherConditions.Rain
        in 85..86 -> WeatherConditions.Snow
        in 90..99 -> WeatherConditions.Thunderstorm
        else -> WeatherConditions.Unknown
    }
}
