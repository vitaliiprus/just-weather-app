package prus.justweatherapp.feature.weather.location.forecast.daily

import prus.justweatherapp.core.ui.UiText
import prus.justweatherapp.feature.weather.location.forecast.daily.temprange.TempRangeModel

data class DailyForecastWeatherUiModel(
    val date: UiText,
    val isWeekend: Boolean,
    var weatherConditions: UiText,
    val conditionImageResId: Int,
    val precipitationProb: String? = null,
    val tempRangeModel: TempRangeModel
)
