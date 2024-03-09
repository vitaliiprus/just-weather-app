package prus.justweatherapp.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import prus.justweatherapp.local.db.model.MainWeatherData
import prus.justweatherapp.local.db.model.Wind

@Entity(
    tableName = "weather",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"]
        )
    ],
    indices = [Index("location_id", "date_time", unique = true)]
)

@Serializable
data class WeatherEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("location_id") val locationId: String,
    @ColumnInfo("date_time") val dateTime: LocalDateTime,
    @Embedded @SerialName("main") val main: MainWeatherData,
    @ColumnInfo("weather_cond") val weatherConditions: Int,
    @ColumnInfo("clouds") val clouds: Double? = null,
    @ColumnInfo("rain") val rain: Double? = null,
    @ColumnInfo("snow") val snow: Double? = null,
    @Embedded @SerialName("wind") val wind: Wind,
    @ColumnInfo("visibility") val visibility: Int,
    @ColumnInfo("pop") val probOfPrecipitations: Double,

    )