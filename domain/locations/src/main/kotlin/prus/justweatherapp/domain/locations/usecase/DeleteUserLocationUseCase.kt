package prus.justweatherapp.domain.locations.usecase

import prus.justweatherapp.domain.locations.repository.UserLocationsRepository
import javax.inject.Inject

class DeleteUserLocationUseCase @Inject constructor(
    private val userLocationsRepository: UserLocationsRepository
) {
    suspend operator fun invoke(
        locationId: String
    ): Result<Pair<String, String>> {
        return userLocationsRepository.getUserLocationById(locationId)?.let { location ->
            val locationName = location.displayName
            userLocationsRepository.deleteUserLocation(locationId)
            userLocationsRepository.getUserLocationById(locationId)?.let {
                Result.failure(Throwable("Cannot delete the location"))
            } ?: Result.success(Pair(locationId, locationName))
        } ?: Result.failure(Throwable("Cannot find the location"))
    }
}