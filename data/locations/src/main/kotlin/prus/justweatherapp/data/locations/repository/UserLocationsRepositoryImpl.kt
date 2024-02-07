package prus.justweatherapp.data.locations.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
        userLocationsDao.addUserLocation(location.toDbEntity())
    }

    override suspend fun getUserLocations(): Flow<List<Location>> {
        return userLocationsDao.getUserLocations().map { it.mapToDomainModels() }
    }

    override suspend fun deleteUserLocation(location: Location) {
        userLocationsDao.getUserLocationById(location.id)?.let {
            userLocationsDao.deleteUserLocation(it)
        }
    }
}