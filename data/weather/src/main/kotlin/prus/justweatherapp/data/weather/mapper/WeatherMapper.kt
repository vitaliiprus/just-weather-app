package prus.justweatherapp.data.weather.mapper

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import prus.justweatherapp.core.common.util.addTimezoneOffset
import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.model.WeatherConditions
import prus.justweatherapp.domain.weather.model.Wind
import prus.justweatherapp.domain.weather.model.scale.PressureScale
import prus.justweatherapp.domain.weather.model.scale.TempScale
import prus.justweatherapp.local.db.entity.SunDataEntity
import prus.justweatherapp.local.db.entity.WeatherEntity
import prus.justweatherapp.remote.model.ForecastResponseDTO
import prus.justweatherapp.remote.model.HourlyWeatherDTO
import prus.justweatherapp.remote.model.SunDataDTO
import kotlin.time.DurationUnit
import kotlin.time.toDuration

internal fun ForecastResponseDTO.mapToDomainModels(locationId: String) =
    this.hourly
        .filter { hourly ->
            this.sun.firstOrNull { it.dateTime.date == hourly.dateTime.date } != null
        }
        .map { hourlyDataDto ->
            val hourlyDataDbo = hourlyDataDto
                .mapToDBO(locationId)
            val sunDataDbo = this.sun
                .first { it.dateTime.date == hourlyDataDto.dateTime.date }
                .mapToDBO(locationId, this.timezoneOffset)
            Pair(hourlyDataDbo, sunDataDbo).mapToDomainModel()
        }

internal fun HourlyWeatherDTO.mapToDBO(locationId: String) =
    WeatherEntity(
        locationId = locationId,
        dateTime = this.dateTime,
        temp = this.temp,
        feelsLike = this.feelsLike,
        humidity = this.humidity,
        pop = this.pop,
        rain = this.rain,
        showers = this.showers,
        snowfall = this.snowfall,
        weatherCode = this.weatherConditions.code,
        pressure = this.pressure,
        cloudCover = this.cloudCover,
        visibility = this.visibility,
        windSpeed = this.windSpeed,
        windDirection = this.windDirection,
        windGusts = this.windGusts,
        uvi = this.uvi,
        timestamp = getTimestamp()
    )

internal fun SunDataDTO.mapToDBO(
    locationId: String,
    timezoneOffset: Int
) = SunDataEntity(
    locationId = locationId,
    date = this.dateTime.date,
    timezoneOffset = timezoneOffset,
    sunrise = this.sunrise,
    sunset = this.sunset,
    daylightDuration = this.daylightDuration,
    sunshineDuration = this.sunshineDuration,
    timestamp = getTimestamp()
)


internal fun Pair<WeatherEntity, SunDataEntity>.mapToDomainModel() =
    Weather(
        locationId = this.first.locationId,
        dateTime = this.first.dateTime.addTimezoneOffset(this.second.timezoneOffset),
        timezoneOffset = this.second.timezoneOffset,
        temp = this.first.temp,
        feelsLike = this.first.feelsLike,
        tempScale = TempScale.KELVIN,
        pressure = this.first.pressure,
        pressureScale = PressureScale.MM_HG,
        humidity = this.first.humidity,
        weatherConditions = WeatherConditions.fromValue(this.first.weatherCode),
        clouds = this.first.cloudCover,
        rain = this.first.rain,
        snow = this.first.snowfall,
        wind = Wind(
            speed = this.first.windSpeed,
            gust = this.first.windGusts,
            degree = this.first.windDirection,
        ),
        visibility = this.first.visibility?.toInt(),
        uvi = this.first.uvi,
        probOfPrecipitations = this.first.pop,
        sunrise = this.second.sunrise.addTimezoneOffset(this.second.timezoneOffset).time,
        sunset = this.second.sunset.addTimezoneOffset(this.second.timezoneOffset).time,
        daylight = this.second.daylightDuration.toDuration(DurationUnit.SECONDS)
    )

//internal fun getDayLight(sunrise: LocalDateTime, sunset: LocalDateTime): Duration {
//    return abs(
//        sunset
//            .toInstant(TimeZone.UTC)
//            .minus(
//                other = sunrise.toInstant(TimeZone.UTC),
//                unit = DateTimeUnit.MINUTE
//            )
//    ).minutes
//}

private fun getTimestamp(): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}
