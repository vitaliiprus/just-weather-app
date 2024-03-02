package prus.justweatherapp.feature.locations.mapper

import prus.justweatherapp.core.ui.R
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.weather.model.CurrentWeather
import prus.justweatherapp.domain.weather.model.WeatherConditions
import prus.justweatherapp.feature.locations.model.CurrentWeatherUiModel
import kotlin.math.roundToInt

fun CurrentWeather.mapToUiModel(): CurrentWeatherUiModel =
    CurrentWeatherUiModel(
        currentTemp = getCurrentTempString(this.currentTemp),
        minMaxTemp = getMinMaxTempString(this.minTemp, this.maxTemp),
        weatherConditions = getWeatherConditionsString(this.weatherConditions),
        conditionImageResId = getWeatherConditionImageResId(this.weatherConditions)
    )

private fun getCurrentTempString(currentTemp: Double): String {
    return "${(currentTemp - 273.15).roundToInt()}º"
}

private fun getMinMaxTempString(minTemp: Double, maxTemp: Double): String {
    return "↓${(minTemp - 273.15).roundToInt()}º ↑${(maxTemp - 273.15).roundToInt()}º"
}

private fun getWeatherConditionsString(weatherConditions: WeatherConditions): UiText {
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

private fun getWeatherConditionImageResId(weatherConditions: WeatherConditions): Int {
    return when (weatherConditions) {
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
    }
}