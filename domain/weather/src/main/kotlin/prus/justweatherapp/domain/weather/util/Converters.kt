package prus.justweatherapp.domain.weather.util

import prus.justweatherapp.domain.weather.model.TempScale

fun convertTemp(
    tempKelvin: Double,
    toScale: TempScale
): Double {
    return when (toScale) {
        TempScale.KELVIN -> tempKelvin
        TempScale.CELSIUS -> tempKelvin - 273.15
        TempScale.FAHRENHEIT -> (tempKelvin - 273.15) * 9/5 + 32
    }
}