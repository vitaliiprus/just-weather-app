package prus.justweatherapp.domain.locations.repository

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.locations.model.Location

interface UserLocationsRepository {

    suspend fun addUserLocation(location: Location)

    fun getUserLocations(): Flow<List<Location>>

    suspend fun deleteUserLocation(location: Location)
}