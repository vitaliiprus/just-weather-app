package prus.justweatherapp.domain.locations.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.locations.model.Location

interface LocationsRepository {

    suspend fun getLocations(query:String): Flow<PagingData<Location>>

    suspend fun getLocationById(locationId: String): Location?
}