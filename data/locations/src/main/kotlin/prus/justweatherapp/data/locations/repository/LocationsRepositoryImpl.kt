package prus.justweatherapp.data.locations.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import prus.justweatherapp.data.locations.mapper.mapToDomainModel
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.domain.locations.repository.LocationsRepository
import prus.justweatherapp.local.db.dao.LocationsDao
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val locationsDao: LocationsDao
) : LocationsRepository {

    override suspend fun getLocations(query: String): Flow<PagingData<Location>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { locationsDao.getLocations("%$query%") }
        ).flow.map { pagingData -> pagingData.map { it.mapToDomainModel() } }
    }

    override suspend fun getLocationById(locationId: String): Location? {
        return locationsDao.getLocationById(locationId)?.mapToDomainModel()
    }
}