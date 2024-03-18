package prus.justweatherapp.data.weather.mapper

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toInstant
import prus.justweatherapp.domain.weather.model.TempScale
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.model.WeatherConditions
import prus.justweatherapp.domain.weather.model.Wind
import prus.justweatherapp.local.db.entity.WeatherEntity
import prus.justweatherapp.local.db.model.MainWeatherDataDBO
import prus.justweatherapp.local.db.model.WindDBO
import prus.justweatherapp.remote.model.CityDTO
import prus.justweatherapp.remote.model.CurrentWeatherDTO
import prus.justweatherapp.remote.model.ForecastWeatherDataDTO
import prus.justweatherapp.remote.model.MainWeatherDataDTO
import prus.justweatherapp.remote.model.WindDTO
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

internal fun CurrentWeatherDTO.mapToDBO(locationId: String) =
    WeatherEntity(
        locationId = locationId,
        dateTime = this.dateTime,
        main = this.main.mapToDBO(),
        weatherConditions = this.weather.getOrNull(0)?.id,
        clouds = this.clouds?.all,
        rain = this.rain?.h1,
        snow = this.snow?.h1,
        wind = this.wind.mapToDBO(),
        visibility = this.visibility,
        sunrise = this.sunTime.sunrise,
        sunset = this.sunTime.sunset,
        timezoneOffset = this.timezoneOffset,
        isForecast = false
    )

internal fun ForecastWeatherDataDTO.mapToDBO(locationId: String, city: CityDTO) =
    WeatherEntity(
        locationId = locationId,
        dateTime = this.dateTime,
        main = this.main.mapToDBO(),
        weatherConditions = this.weather.firstOrNull()?.id,
        clouds = this.clouds?.all,
        rain = this.rain?.h3,
        snow = this.snow?.h3,
        wind = this.wind.mapToDBO(),
        visibility = this.visibility,
        probOfPrecipitations = this.probOfPrecipitations,
        sunrise = city.sunrise,
        sunset = city.sunset,
        timezoneOffset = city.timezoneOffset,
        isForecast = true
    )

internal fun MainWeatherDataDTO.mapToDBO() =
    MainWeatherDataDBO(
        temp = this.temp,
        feelsLike = this.feelsLike,
        tempMin = this.tempMin,
        tempMax = this.tempMax,
        pressure = this.pressure,
        humidity = this.humidity,
    )

internal fun WeatherEntity.mapToDomainModel() =
    Weather(
        locationId = this.locationId,
        dateTime = this.dateTime,
        timezoneOffset = this.timezoneOffset,
        temp = this.main.temp,
        feelsLike = this.main.feelsLike,
        tempMin = this.main.tempMin,
        tempMax = this.main.tempMax,
        tempScale = TempScale.KELVIN,
        pressure = this.main.pressure,
        humidity = this.main.humidity,
        weatherConditions = mapWeatherConditionsIdToDomainModel(this.weatherConditions),
        clouds = this.clouds,
        rain = this.rain,
        snow = this.snow,
        wind = this.wind.mapToDomainModel(),
        visibility = this.visibility,
        probOfPrecipitations = this.probOfPrecipitations,
        sunrise = this.sunrise,
        sunset = this.sunset,
        daylight = getDayLight(this.sunrise, this.sunset)
    )

internal fun CurrentWeatherDTO.mapToDomainModel(locationId: String) =
    mapToDBO(locationId).mapToDomainModel()

internal fun WindDTO.mapToDBO() =
    WindDBO(
        speed = this.speed,
        gust = this.gust,
        degree = this.degree,
    )

internal fun WindDBO.mapToDomainModel() =
    Wind(
        speed = this.speed,
        gust = this.gust,
        degree = this.degree,
    )

internal fun mapWeatherConditionsIdToDomainModel(weatherConditionsId: Int?): WeatherConditions {
    return when (weatherConditionsId) {
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

internal fun getDayLight(sunrise: LocalDateTime, sunset: LocalDateTime): Duration {
    return sunrise
        .toInstant(TimeZone.UTC)
        .minus(
            other = sunset.toInstant(TimeZone.UTC),
            unit = DateTimeUnit.MINUTE
        ).minutes
}
