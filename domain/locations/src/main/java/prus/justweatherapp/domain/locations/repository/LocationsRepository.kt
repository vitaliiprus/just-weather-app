package prus.justweatherapp.domain.locations.repository

import prus.justweatherapp.domain.locations.model.Location

interface LocationsRepository {
    
    suspend fun getLocations(skip: Int, take: Int): List<Location>

    suspend fun getLocationsWithMask(mask: String): List<Location>

    suspend fun getLocationById(locationId: String): Location?
}