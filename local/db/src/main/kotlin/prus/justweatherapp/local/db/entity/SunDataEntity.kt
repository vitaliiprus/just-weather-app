package prus.justweatherapp.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(
    tableName = "sun_data",
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"]
        )
    ],
    primaryKeys = ["location_id", "date"],
    indices = [Index("location_id", "date", unique = true)]
)

@Serializable
data class SunDataEntity(
    @ColumnInfo("location_id") val locationId: String,
    @ColumnInfo("date") val date: LocalDate,
    @SerialName("timezone_offset") val timezoneOffset: Int,
    @ColumnInfo("sunrise") val sunrise: LocalDateTime,
    @ColumnInfo("sunset") val sunset: LocalDateTime,
    @ColumnInfo("daylight_duration") val daylightDuration: Double,
    @ColumnInfo("sunshine_duration") val sunshineDuration: Double,
    @ColumnInfo("timestamp") val timestamp: LocalDateTime
)