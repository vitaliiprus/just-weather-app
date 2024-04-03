package prus.justweatherapp.domain.weather.model

enum class WeatherConditions(val code: Int) {
    Unknown(-1),
    Clear(0),
    MostlySunny(1),
    MostlyCloudy(2),
    Cloudy(3),
    ChanceRain(4),
    ChanceSleet(5),
    Rain(6),
    Sleet(7),
    Snow(8),
    Fog(9),
    Thunderstorm(10);

    companion object {
        fun fromValue(code: Int): WeatherConditions {
            return entries.first { it.code == code }
        }
    }
}
