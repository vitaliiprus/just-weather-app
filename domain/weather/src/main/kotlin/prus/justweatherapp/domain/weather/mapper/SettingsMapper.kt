package prus.justweatherapp.domain.weather.mapper

import prus.justweatherapp.domain.weather.model.scale.PressureScale
import prus.justweatherapp.domain.weather.model.scale.TempScale
import prus.justweatherapp.domain.weather.model.scale.WindScale
import prus.justweatherapp.domain.settings.model.scale.PressureScale as PressureScaleSettings
import prus.justweatherapp.domain.settings.model.scale.TempScale as TempScaleSettings
import prus.justweatherapp.domain.settings.model.scale.WindScale as WindScaleSettings

fun TempScaleSettings.toDomainModel(): TempScale {
    return when (this) {
        TempScaleSettings.KELVIN -> TempScale.KELVIN
        TempScaleSettings.CELSIUS -> TempScale.CELSIUS
        TempScaleSettings.FAHRENHEIT -> TempScale.FAHRENHEIT
    }
}

fun PressureScaleSettings.toDomainModel(): PressureScale {
    return when (this) {
        PressureScaleSettings.MM_HG -> PressureScale.MM_HG
        PressureScaleSettings.H_PA -> PressureScale.H_PA
    }
}

fun WindScaleSettings.toDomainModel(): WindScale {
    return when (this) {
        WindScaleSettings.M_S -> WindScale.M_S
        WindScaleSettings.KM_H -> WindScale.KM_H
        WindScaleSettings.MPH -> WindScale.MPH
        WindScaleSettings.KT -> WindScale.KT
    }
}