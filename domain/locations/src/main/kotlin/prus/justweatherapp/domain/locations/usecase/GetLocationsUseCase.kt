package prus.justweatherapp.domain.locations.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.domain.locations.repository.LocationsRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(
        query: String
    ): Flow<PagingData<Location>> {
        return locationsRepository.getLocations(query)
    }
}