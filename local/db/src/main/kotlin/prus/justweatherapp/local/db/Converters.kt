package prus.justweatherapp.local.db

import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

class Converters {
    @TypeConverter
    fun localDateTimeToDatestamp(dateTime: LocalDateTime): Long {
        return dateTime.toInstant(TimeZone.UTC).toEpochMilliseconds() / 1000
    }

    @TypeConverter
    fun datestampToLocalDateTime(value: Long): LocalDateTime =
        Instant.fromEpochSeconds(value).toLocalDateTime(TimeZone.UTC)
}