package prus.justweatherapp.domain.weather.util

import prus.justweatherapp.domain.weather.model.scale.PressureScale
import prus.justweatherapp.domain.weather.model.scale.TempScale
import prus.justweatherapp.domain.weather.model.scale.WindScale

fun convertTemp(
    tempKelvin: Double,
    toScale: TempScale
): Double {
    return when (toScale) {
        TempScale.KELVIN -> tempKelvin
        TempScale.CELSIUS -> tempKelvin - 273.15
        TempScale.FAHRENHEIT -> (tempKelvin - 273.15) * 9 / 5 + 32
    }
}

fun convertPressure(
    pressureHpa: Double,
    toScale: PressureScale
): Double {
    return when (toScale) {
        PressureScale.MM_HG -> pressureHpa * 0.75
        PressureScale.H_PA -> pressureHpa
    }
}

fun convertWind(
    windMs: Double,
    toScale: WindScale
): Double {
    return when (toScale) {
        WindScale.M_S -> windMs
        WindScale.KM_H -> windMs * 3.6
        WindScale.MPH -> windMs * 2.237
        WindScale.KT -> windMs * 1.944
    }
}