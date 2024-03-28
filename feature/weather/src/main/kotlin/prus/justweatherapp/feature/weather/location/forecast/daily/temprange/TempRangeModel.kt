package prus.justweatherapp.feature.weather.location.forecast.daily.temprange

data class TempRangeModel(
    val dayMinTemp:Double,
    val dayMaxTemp:Double,
    val rangeMinTemp:Double,
    val rangeMaxTemp:Double,
    val currentTemp:Double?=null,
)
