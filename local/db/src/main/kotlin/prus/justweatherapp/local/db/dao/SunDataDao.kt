package prus.justweatherapp.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import prus.justweatherapp.local.db.entity.SunDataEntity

@Dao
interface SunDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sunDataEntities: List<SunDataEntity>)

    @Query("SELECT * FROM sun_data WHERE location_id = :locationId AND date >= :dateFrom LIMIT :limit")
    suspend fun getDataByLocationId(
        locationId: String,
        dateFrom: LocalDate,
        limit:Int
    ): List<SunDataEntity>

    @Query("DELETE FROM sun_data WHERE date < :dateTo")
    suspend fun deleteOutdatedEntities(
        dateTo: LocalDate = Clock.System.now()
            .plus(-24, DateTimeUnit.HOUR)
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
    )

    @Query("SELECT COUNT() FROM sun_data")
    suspend fun getDataCount(): Int
}