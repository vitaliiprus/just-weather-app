package prus.justweatherapp.data.locations.repository

import prus.justweatherapp.data.locations.mapper.mapToDomainModel
import prus.justweatherapp.data.locations.mapper.mapToDomainModels
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.domain.locations.repository.LocationsRepository
import prus.justweatherapp.local.db.dao.LocationsDao
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val locationsDao: LocationsDao
) : LocationsRepository {

    override suspend fun getLocations(skip: Int, take: Int): List<Location> {
        return locationsDao.getLocations(skip, take).mapToDomainModels()
    }

    override suspend fun getLocationsWithMask(mask: String): List<Location> {
        return locationsDao.getLocationsWithMask(mask).mapToDomainModels()
    }

    override suspend fun getLocationById(locationId: String): Location? {
        return locationsDao.getLocationById(locationId)?.mapToDomainModel()
    }
}