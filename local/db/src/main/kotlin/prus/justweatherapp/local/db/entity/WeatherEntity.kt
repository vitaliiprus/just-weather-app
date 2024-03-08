package prus.justweatherapp.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
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
            parentColumns = ["location_id"],
            childColumns = ["location_id"]
        )
    ],
    indices = [Index("location_id", "dt", unique = true)]
)

@Serializable
data class WeatherEntity(

//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "id")
//    val id: Int,

    @ColumnInfo(name = "location_id") val locationId: String,
    @SerialName("dt") val dateTime: LocalDateTime,
    @Embedded @SerialName("main") val main: MainWeatherData,
    @SerialName("weather_cond") val weatherConditions: Int,
    @SerialName("clouds") val clouds: Double? = null,
    @SerialName("rain") val rain: Double? = null,
    @SerialName("snow") val snow: Double? = null,
    @Embedded @SerialName("wind") val wind: Wind,
    @SerialName("visibility") val visibility: Int,
    @SerialName("pop") val probOfPrecipitations: Double,

    )