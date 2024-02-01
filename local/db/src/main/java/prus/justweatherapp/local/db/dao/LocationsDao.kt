package prus.justweatherapp.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import prus.justweatherapp.local.db.entity.LocationEntity

@Dao
interface LocationsDao {

    @Query("SELECT * FROM locations ORDER BY city")
    suspend fun getAllLocations(): List<LocationEntity>

    @Query("SELECT * FROM locations ORDER BY id LIMIT :limit OFFSET :offset")
    suspend fun getLocations(offset: Int, limit: Int): List<LocationEntity>

    @Query("SELECT * FROM locations WHERE city LIKE :mask OR admin_name LIKE :mask OR country LIKE :mask")
    suspend fun getLocationsWithMask(mask: String = ""): List<LocationEntity>

    @Query("SELECT * FROM locations WHERE location_id = :locationId")
    suspend fun getLocationById(locationId: String): LocationEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locationEntities: List<LocationEntity>)
}