package prus.justweatherapp.feature.weather.mapper

import prus.justweatherapp.core.ui.R
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.weather.model.WeatherConditions

fun getWeatherConditionsString(weatherConditions: WeatherConditions): UiText {
    return UiText.StringResource(
        when (weatherConditions) {
            WeatherConditions.Clear -> R.string.clear
            WeatherConditions.MostlySunny -> R.string.mostly_sunny
            WeatherConditions.MostlyCloudy -> R.string.mostly_cloudy
            WeatherConditions.Cloudy -> R.string.cloudy
            WeatherConditions.ChanceRain -> R.string.chance_of_rain
            WeatherConditions.ChanceSleet -> R.string.chance_of_sleet
            WeatherConditions.Rain -> R.string.rain
            WeatherConditions.Sleet -> R.string.sleet
            WeatherConditions.Snow -> R.string.snow
            WeatherConditions.Fog -> R.string.fog
            WeatherConditions.Thunderstorm -> R.string.thunderstorm
            WeatherConditions.Unknown -> R.string.cloudy
        }
    )
}

fun getWeatherConditionImageResId(
    weatherConditions: WeatherConditions,
    isDay: Boolean = true
): Int {
    return if (isDay)
        when (weatherConditions) {
            WeatherConditions.Clear -> R.drawable.clear
            WeatherConditions.MostlySunny -> R.drawable.mostlysunny
            WeatherConditions.MostlyCloudy -> R.drawable.mostlycloudy
            WeatherConditions.Cloudy -> R.drawable.cloudy
            WeatherConditions.ChanceRain -> R.drawable.chancerain
            WeatherConditions.ChanceSleet -> R.drawable.chancesleet
            WeatherConditions.Rain -> R.drawable.rain
            WeatherConditions.Sleet -> R.drawable.sleet
            WeatherConditions.Snow -> R.drawable.snow
            WeatherConditions.Fog -> R.drawable.fog
            WeatherConditions.Thunderstorm -> R.drawable.thunderstorm
            WeatherConditions.Unknown -> R.drawable.cloudy
        } else
        when (weatherConditions) {
            WeatherConditions.Clear -> R.drawable.nt_clear
            WeatherConditions.MostlySunny -> R.drawable.nt_mostlysunny
            WeatherConditions.MostlyCloudy -> R.drawable.nt_mostlycloudy
            WeatherConditions.Cloudy -> R.drawable.nt_cloudy
            WeatherConditions.ChanceRain -> R.drawable.nt_chancerain
            WeatherConditions.ChanceSleet -> R.drawable.nt_chancesleet
            WeatherConditions.Rain -> R.drawable.nt_rain
            WeatherConditions.Sleet -> R.drawable.nt_sleet
            WeatherConditions.Snow -> R.drawable.snow
            WeatherConditions.Fog -> R.drawable.nt_fog
            WeatherConditions.Thunderstorm -> R.drawable.thunderstorm
            WeatherConditions.Unknown -> R.drawable.nt_cloudy
        }
}