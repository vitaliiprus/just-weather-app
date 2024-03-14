package prus.justweatherapp.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.local.db.model.MainWeatherDataDBO
import prus.justweatherapp.local.db.model.WindDBO

@Entity(
    tableName = "weather",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"]
        )
    ],
    primaryKeys = ["location_id", "date_time"],
    indices = [Index("location_id", "date_time", unique = true)]
)

@Serializable
data class WeatherEntity(
    @ColumnInfo("location_id") val locationId: String,
    @ColumnInfo("date_time") val dateTime: LocalDateTime,
    @Embedded @SerialName("main") val main: MainWeatherDataDBO,
    @ColumnInfo("weather_cond") val weatherConditions: Int? = null,
    @ColumnInfo("clouds") val clouds: Double? = null,
    @ColumnInfo("rain") val rain: Double? = null,
    @ColumnInfo("snow") val snow: Double? = null,
    @Embedded(prefix = "wind") @SerialName("wind") val wind: WindDBO,
    @ColumnInfo("visibility") val visibility: Int? = null,
    @ColumnInfo("pop") val probOfPrecipitations: Double? = null,
    @ColumnInfo("sunrise") val sunrise: LocalDateTime,
    @ColumnInfo("sunset") val sunset: LocalDateTime,
    @SerialName("timezone_offset") val timezoneOffset: Int,
    @ColumnInfo(name = "is_forecast") val isForecast: Boolean = false
)