package prus.justweatherapp.data.weather.mapper

import prus.justweatherapp.domain.weather.model.CurrentWeather
import prus.justweatherapp.domain.weather.model.ForecastWeather
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

internal fun CurrentWeatherDTO.mapToDBO(locationId: String) =
    WeatherEntity(
        locationId = locationId,
        dateTime = this.dateTime,
        main = this.main.mapToDBO(),
        weatherConditions = this.weather.first().id,
        clouds = this.clouds?.all,
        rain = this.rain?.h1,
        snow = this.snow?.h1,
        wind = this.wind.mapToDBO(),
        visibility = this.visibility,
        sunrise = this.sunTime.sunrise,
        sunset = this.sunTime.sunset,
        timezoneOffset = this.timezoneOffset
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
        timezoneOffset = city.timezoneOffset
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

internal fun WindDTO.mapToDBO() =
    WindDBO(
        speed = this.speed,
        gust = this.gust,
        degree = this.degree,
    )

internal fun CurrentWeatherDTO.mapToCurrentWeatherDomainModel(locationId: String) =
    CurrentWeather(
        locationId = locationId,
        timezoneOffset = this.timezoneOffset,
        currentTemp = this.main.temp,
        minTemp = this.main.tempMin,
        maxTemp = this.main.tempMax,
        weatherConditions = mapWeatherConditionsIdToDomainModel(this.weather.getOrNull(0)?.id),
    )

internal fun WeatherEntity.mapToCurrentWeatherDomainModel() =
    CurrentWeather(
        locationId = this.locationId,
        timezoneOffset = this.timezoneOffset,
        currentTemp = this.main.temp,
        minTemp = this.main.tempMin,
        maxTemp = this.main.tempMax,
        weatherConditions = mapWeatherConditionsIdToDomainModel(this.weatherConditions),
    )

internal fun WeatherEntity.mapToForecastWeatherDomainModel() =
    ForecastWeather(
        locationId = this.locationId,
        dateTime = this.dateTime,
        temp = this.main.temp,
        feelsLike = this.main.feelsLike,
        tempMin = this.main.tempMin,
        tempMax = this.main.tempMax,
        pressure = this.main.pressure,
        humidity = this.main.humidity,
        weather = mapWeatherConditionsIdToDomainModel(this.weatherConditions),
        clouds = this.clouds,
        rain = this.rain,
        snow = this.snow,
        wind = this.wind.mapToDomainModel(),
        visibility = this.visibility,
        probOfPrecipitations = this.probOfPrecipitations
    )

internal fun WindDBO.mapToDomainModel() =
    Wind(
        speed = this.speed,
        gust = this.gust,
        degree = this.degree,
    )

fun mapWeatherConditionsIdToDomainModel(weatherConditionsId: Int?): WeatherConditions {
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
