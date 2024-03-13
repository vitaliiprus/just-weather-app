package prus.justweatherapp.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import prus.justweatherapp.local.db.entity.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weatherEntities: List<WeatherEntity>)

    @Query("SELECT * FROM weather WHERE location_id = :locationId AND date_time > :dateFrom ORDER BY date_time DESC LIMIT 1")
    suspend fun getCurrentWeatherByLocationId(
        locationId: String,
        dateFrom: LocalDateTime = Clock.System.now()
            .plus(-3, DateTimeUnit.HOUR)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    ): WeatherEntity?

    @Query("DELETE FROM weather WHERE date_time < :dateTo")
    suspend fun deleteOutdatedEntities(
        dateTo: LocalDateTime = Clock.System.now()
            .plus(-3, DateTimeUnit.HOUR)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )

    @Query("SELECT COUNT() FROM weather")
    suspend fun getWeatherCount(): Int
}