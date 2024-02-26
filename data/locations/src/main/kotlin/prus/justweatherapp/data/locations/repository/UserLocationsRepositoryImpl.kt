package prus.justweatherapp.data.locations.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import prus.justweatherapp.data.locations.mapper.mapToDomainModel
import prus.justweatherapp.data.locations.mapper.mapToDomainModels
import prus.justweatherapp.data.locations.mapper.toDbEntity
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.domain.locations.repository.UserLocationsRepository
import prus.justweatherapp.local.db.dao.UserLocationsDao
import javax.inject.Inject

class UserLocationsRepositoryImpl @Inject constructor(
    private val userLocationsDao: UserLocationsDao
) : UserLocationsRepository {

    override suspend fun addUserLocation(location: Location) {
        val userLocationsCount = userLocationsDao.getUserLocationsCount()
        location.orderIndex = userLocationsCount
        userLocationsDao.addUserLocation(location.toDbEntity())
    }

    override suspend fun updateUserLocationDisplayName(
        locationId: String,
        newDisplayName: String
    ) {
        userLocationsDao.updateUserLocationDisplayName(locationId, newDisplayName)
    }

    override suspend fun updateUserLocationsOrderIndices(
        locationsIdsOrderIndices: List<Pair<String, Int>>
    ) {
        userLocationsDao.updateUserLocationsOrderIndices(locationsIdsOrderIndices)
    }

    override fun getUserLocations(): Flow<List<Location>> {
        return userLocationsDao.getUserLocations().map {
            it.mapToDomainModels()
        }
    }

    override suspend fun getUserLocationById(locationId: String): Location? {
        return userLocationsDao.getUserLocationById(locationId)?.mapToDomainModel()
    }

    override suspend fun deleteUserLocation(locationId: String) {
        userLocationsDao.deleteUserLocation(locationId)
    }
}