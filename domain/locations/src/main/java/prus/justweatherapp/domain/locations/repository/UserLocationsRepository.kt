package prus.justweatherapp.domain.locations.repository

import prus.justweatherapp.domain.locations.model.Location

interface UserLocationsRepository {

    suspend fun addUserLocation(location: Location)

    suspend fun getUserLocations(): List<Location>

    suspend fun deleteUserLocation(location: Location)
}