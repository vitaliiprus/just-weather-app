package prus.justweatherapp.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.local.db.entity.UserLocationEntity
import prus.justweatherapp.local.db.model.UserLocationDbModel

@Dao
interface UserLocationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUserLocation(userLocation: UserLocationEntity)

    @Query("UPDATE user_locations SET display_name = :newDisplayName WHERE location_id = :locationId")
    suspend fun updateUserLocationDisplayName(locationId: String, newDisplayName: String)

    @Query("SELECT COUNT() FROM user_locations")
    suspend fun getUserLocationsCount(): Int

    @Query("SELECT * FROM user_locations JOIN locations ON locations.location_id = user_locations.location_id ORDER BY order_index")
    fun getUserLocations(): Flow<List<UserLocationDbModel>>

    @Query("SELECT * FROM user_locations JOIN locations ON locations.location_id = user_locations.location_id WHERE user_locations.location_id = :locationId")
    suspend fun getUserLocationById(locationId: String): UserLocationDbModel?

    @Query("DELETE FROM user_locations WHERE location_id = :locationId")
    suspend fun deleteUserLocation(locationId: String)
}