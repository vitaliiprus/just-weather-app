package prus.justweatherapp.local.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import prus.justweatherapp.local.db.entity.LocationEntity

@Dao
interface LocationsDao {

    @Query("SELECT * FROM locations ORDER BY city")
    suspend fun getAllLocations(): List<LocationEntity>

    @Query("SELECT * FROM locations WHERE city LIKE :query OR admin_name LIKE :query OR country LIKE :query ORDER BY city")
    fun getLocations(query: String = "%"): PagingSource<Int, LocationEntity>

    @Query("SELECT * FROM locations WHERE location_id = :locationId")
    suspend fun getLocationById(locationId: String): LocationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locationEntities: List<LocationEntity>)
}