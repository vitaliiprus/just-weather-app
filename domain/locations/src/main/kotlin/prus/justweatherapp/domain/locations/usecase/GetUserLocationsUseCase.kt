package prus.justweatherapp.domain.locations.usecase

import kotlinx.coroutines.flow.Flow
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.domain.locations.repository.UserLocationsRepository
import javax.inject.Inject

class GetUserLocationsUseCase @Inject constructor(
    private val userLocationsRepository: UserLocationsRepository
) {
    suspend operator fun invoke(
    ): Flow<List<Location>> {
        return userLocationsRepository.getUserLocations()
    }
}