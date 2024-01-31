package prus.justweatherapp.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import prus.justweatherapp.local.db.entity.LocationEntity

@Dao
interface LocationDao {

    @Query("SELECT * FROM locations ORDER BY city")
    suspend fun getAllLocations(): List<LocationEntity>

    @Query("SELECT * FROM locations WHERE id = :locationId")
    suspend fun getLocationById(locationId: String): LocationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locationEntities: List<LocationEntity>)
}