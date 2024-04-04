package prus.justweatherapp.remote.openweather.mapper

import prus.justweatherapp.remote.model.ForecastResponseDTO
import prus.justweatherapp.remote.model.HourlyWeatherDTO
import prus.justweatherapp.remote.model.SunDataDTO
import prus.justweatherapp.remote.model.WeatherConditions
import prus.justweatherapp.remote.openweather.model.ForecastWeatherDTO
import prus.justweatherapp.remote.openweather.model.ForecastWeatherDataDTO

internal fun Result<ForecastWeatherDTO>.mapToForecastResponseDto() =
    this.map {
        ForecastResponseDTO(
            timezoneOffset = it.city.timezoneOffset,
            hourly = it.list.mapToWeatherDtoList(),
            sun = listOf(
                SunDataDTO(
                    dateTime = it.list.first().dateTime,
                    sunrise = it.city.sunrise,
                    sunset = it.city.sunset,
                    daylightDuration = 0.0,
                    sunshineDuration = 0.0
                )
            )
        )
    }

internal fun List<ForecastWeatherDataDTO>.mapToWeatherDtoList() =
    this.map {
        HourlyWeatherDTO(
            dateTime = it.dateTime,
            temp = it.main.temp,
            feelsLike = it.main.feelsLike,
            humidity = it.main.humidity,
            pressure = it.main.pressure,
            pop = it.probOfPrecipitations?.times(100) ?: 0.0,
            rain = it.rain?.h1 ?: 0.0,
            showers = it.rain?.h1 ?: 0.0,
            snowfall = it.snow?.h1 ?: 0.0,
            weatherConditions = mapToWeatherConditions(it.weather.firstOrNull()?.id ?: -1),
            cloudCover = it.clouds?.all ?: 0.0,
            visibility = it.visibility?.toDouble() ?: 0.0,
            windSpeed = it.wind.speed ?: 0.0,
            windDirection = it.wind.degree ?: 0.0,
            windGusts = it.wind.gust ?: 0.0,
            uvi = 0.0

        )
    }

internal fun mapToWeatherConditions(weatherCode: Int?): WeatherConditions {
    return when (weatherCode) {
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