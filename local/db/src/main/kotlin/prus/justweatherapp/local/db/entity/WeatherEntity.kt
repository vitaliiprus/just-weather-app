package prus.justweatherapp.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

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
    @ColumnInfo("temp") val temp: Double,
    @ColumnInfo("feels_like") val feelsLike: Double,
    @ColumnInfo("humidity") val humidity: Double,
    @ColumnInfo("pop") val pop: Double?,
    @ColumnInfo("rain") val rain: Double?,
    @ColumnInfo("showers") val showers: Double?,
    @ColumnInfo("snowfall") val snowfall: Double?,
    @ColumnInfo("weather_code")  val weatherCode: Int,
    @ColumnInfo("pressure") val pressure: Double,
    @ColumnInfo("cloud_cover") val cloudCover: Double?,
    @ColumnInfo("visibility") val visibility: Double?,
    @ColumnInfo("wind_speed") val windSpeed: Double?,
    @ColumnInfo("wind_direction")  val windDirection: Double?,
    @ColumnInfo("wind_gusts")  val windGusts: Double?,
    @ColumnInfo("uvi") val uvi: Double?,
    @ColumnInfo("timestamp") val timestamp: LocalDateTime
)