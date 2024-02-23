package prus.justweatherapp.domain.locations.repository

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.locations.model.Location

interface UserLocationsRepository {

    suspend fun addUserLocation(location: Location)

    suspend fun updateUserLocationDisplayName(locationId: String, newDisplayName: String)

    fun getUserLocations(): Flow<List<Location>>

    suspend fun getUserLocationById(locationId: String): Location?

    suspend fun deleteUserLocation(locationId: String)
}