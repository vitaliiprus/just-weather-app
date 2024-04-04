package prus.justweatherapp.feature.weather.mapper

import prus.justweatherapp.core.ui.R
import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.domain.weather.model.WeatherConditions
import prus.justweatherapp.domain.weather.model.Wind
import prus.justweatherapp.domain.weather.model.WindDirection
import prus.justweatherapp.domain.weather.model.scale.PressureScale
import prus.justweatherapp.domain.weather.model.scale.TempScale
import prus.justweatherapp.domain.weather.model.scale.WindScale
import kotlin.math.ceil
import kotlin.math.roundToInt

fun getTempString(
    temp: Double,
    withScaleUnits: Boolean = false,
    tempScale: TempScale? = null
): String {
    if (!withScaleUnits) {
        return "${temp.roundToInt()}º"
    } else {
        requireNotNull(tempScale).let {
            return when (it) {
                TempScale.KELVIN -> "${temp.roundToInt()}K"
                TempScale.CELSIUS -> "${temp.roundToInt()}ºC"
                TempScale.FAHRENHEIT -> "${temp.roundToInt()}ºF"
            }
        }
    }
}

fun getTempMinMaxString(
    tempMin: Double?,
    tempMax: Double?,
): String {
    val tempMinString = tempMin?.let {
        getTempString(tempMin, false)
    } ?: "-"
    val tempMaxString = tempMax?.let {
        getTempString(tempMax, false)
    } ?: "-"
    return "↓${tempMinString} ↑${tempMaxString}"
}

fun getPressureString(pressure: Double, pressureScale: PressureScale): UiText {
    val pressureScaleStringResId = when (pressureScale) {
        PressureScale.MM_HG -> prus.justweatherapp.feature.weather.R.string.scale_mm_hg
        PressureScale.H_PA -> prus.justweatherapp.feature.weather.R.string.scale_hpa
    }
    return UiText.StringResource(
        id = prus.justweatherapp.feature.weather.R.string.template_value_scale,
        args = arrayOf(
            pressure.roundToInt().toString(),
            UiText.StringResource(pressureScaleStringResId)
        )
    )
}

fun getPrecipitationProbString(probOfPrecipitations: Double?): String? {
    return if (probOfPrecipitations == null || probOfPrecipitations == 0.0)
        null
    else "${ceil(probOfPrecipitations).roundToInt()}%"

}

fun getWindString(wind: Wind?): UiText {
    if (wind == null)
        return UiText.DynamicString("-")

    val windScaleStringResId = when (wind.windScale) {
        WindScale.M_S -> prus.justweatherapp.feature.weather.R.string.scale_m_s
        WindScale.KM_H -> prus.justweatherapp.feature.weather.R.string.scale_km_h
        WindScale.MPH -> prus.justweatherapp.feature.weather.R.string.scale_mph
        WindScale.KT -> prus.justweatherapp.feature.weather.R.string.scale_kt
    }
    var args = arrayOf(
        (wind.speed ?: 0.0).roundToInt().toString(),
        UiText.StringResource(windScaleStringResId)
    )
    return if (wind.getDirection() == WindDirection.Undefined) {
        UiText.StringResource(
            id = prus.justweatherapp.feature.weather.R.string.template_value_scale,
            args = args
        )
    } else {
        args = args.plus(getWindDirectionString(wind.getDirection()))
        UiText.StringResource(
            id = prus.justweatherapp.feature.weather.R.string.template_wind_value,
            args = args
        )
    }
}

fun getWindDirectionString(direction: WindDirection): UiText {
    return when (direction) {
        WindDirection.N -> UiText.StringResource(prus.justweatherapp.feature.weather.R.string.wind_n)
        WindDirection.NE -> UiText.StringResource(prus.justweatherapp.feature.weather.R.string.wind_ne)
        WindDirection.E -> UiText.StringResource(prus.justweatherapp.feature.weather.R.string.wind_e)
        WindDirection.SE -> UiText.StringResource(prus.justweatherapp.feature.weather.R.string.wind_se)
        WindDirection.S -> UiText.StringResource(prus.justweatherapp.feature.weather.R.string.wind_s)
        WindDirection.SW -> UiText.StringResource(prus.justweatherapp.feature.weather.R.string.wind_sw)
        WindDirection.W -> UiText.StringResource(prus.justweatherapp.feature.weather.R.string.wind_w)
        WindDirection.NW -> UiText.StringResource(prus.justweatherapp.feature.weather.R.string.wind_nw)
        WindDirection.Undefined -> UiText.DynamicString("")
    }
}

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