package prus.justweatherapp.domain.weather.util

import prus.justweatherapp.domain.weather.model.Weather
import prus.justweatherapp.domain.weather.model.scale.PressureScale
import prus.justweatherapp.domain.weather.model.scale.TempScale
import prus.justweatherapp.domain.weather.model.scale.WindScale

internal fun getWeatherWithConvertedUnits(
    data: Weather,
    tempScale: TempScale,
    pressureScale: PressureScale,
    windScale: WindScale
): Weather {
    return data.copy(
        temp = convertTemp(data.temp, tempScale),
        feelsLike = convertTemp(data.feelsLike, tempScale),
        tempMin = if (data.tempMin != null)
            convertTemp(data.tempMin, tempScale) else null,
        tempMax = if (data.tempMax != null)
            convertTemp(data.tempMax, tempScale) else null,
        tempScale = tempScale,
        pressure = convertPressure(data.pressure, pressureScale),
        pressureScale = pressureScale,
        wind = data.wind?.copy(
            speed = data.wind.speed?.let {
                convertWind(it, windScale)
            },
            gust = data.wind.gust?.let {
                convertWind(it, windScale)
            },
            windScale = windScale
        )
    )
}

private fun convertTemp(
    tempKelvin: Double,
    toScale: TempScale
): Double {
    return when (toScale) {
        TempScale.KELVIN -> tempKelvin
        TempScale.CELSIUS -> tempKelvin - 273.15
        TempScale.FAHRENHEIT -> (tempKelvin - 273.15) * 9 / 5 + 32
    }
}

private fun convertPressure(
    pressureHpa: Double,
    toScale: PressureScale
): Double {
    return when (toScale) {
        PressureScale.MM_HG -> pressureHpa * 0.75
        PressureScale.H_PA -> pressureHpa
    }
}

private fun convertWind(
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
