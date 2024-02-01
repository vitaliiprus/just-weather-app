package prus.justweatherapp.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import prus.justweatherapp.local.db.entity.UserLocationEntity
import prus.justweatherapp.local.db.model.UserLocationDbModel

@Dao
interface UserLocationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserLocation(userLocation: UserLocationEntity)

    @Query("SELECT COUNT() FROM user_locations")
    suspend fun getUserLocationsCount(): Int

    @Query("SELECT * FROM user_locations JOIN locations ON locations.location_id = user_locations.location_id")
    suspend fun getUserLocations(): List<UserLocationDbModel>
}