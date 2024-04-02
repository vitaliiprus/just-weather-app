package prus.justweatherapp.remote.openmeteo.mapper

import prus.justweatherapp.remote.model.ForecastResponseDTO
import prus.justweatherapp.remote.model.SunDataDTO
import prus.justweatherapp.remote.model.WeatherDTO
import prus.justweatherapp.remote.openmeteo.model.OpenMeteoResponseDTO

internal fun Result<OpenMeteoResponseDTO>.mapToForecastResponseDto() =
    this.map {
        ForecastResponseDTO(
            timezoneOffset = it.timezoneOffset,
            hourly = List(it.hourly.dateTime.size) { index ->
                WeatherDTO(
                    dateTime = it.hourly.dateTime[index],
                    temp = it.hourly.temp[index],
                    feelsLike = it.hourly.feelsLike[index],
                    humidity = it.hourly.humidity[index],
                    pop = it.hourly.pop[index],
                    rain = it.hourly.rain[index],
                    showers = it.hourly.showers[index],
                    snowfall = it.hourly.snowfall[index],
                    weatherCode = it.hourly.weatherCode[index],
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