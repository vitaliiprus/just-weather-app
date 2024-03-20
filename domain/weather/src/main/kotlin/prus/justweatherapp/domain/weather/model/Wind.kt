package prus.justweatherapp.domain.weather.model

import prus.justweatherapp.domain.weather.model.scale.WindScale

data class Wind(
    val speed: Double?,
    val degree: Double?,
    val gust: Double?,
    val windScale: WindScale = WindScale.M_S
) {
    fun getDirection(): WindDirection {
        return when (degree) {
            null -> WindDirection.Undefined
            in 0.0..22.5 -> WindDirection.N
            in 22.5..67.5 -> WindDirection.NE
            in 67.5..112.5 -> WindDirection.E
            in 112.5..157.5 -> WindDirection.SE
            in 157.5..202.5 -> WindDirection.S
            in 202.5..247.5 -> WindDirection.SW
            in 247.5..292.5 -> WindDirection.W
            in 292.5..337.5 -> WindDirection.NW
            in 337.5..360.0 -> WindDirection.N
            else -> WindDirection.Undefined
        }
    }
}

enum class WindDirection {
    N,
    NE,
    E,
    SE,
    S,
    SW,
    W,
    NW,
    Undefined
}