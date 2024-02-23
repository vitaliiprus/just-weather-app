package prus.justweatherapp.domain.locations.usecase

import prus.justweatherapp.domain.locations.repository.LocationsRepository
import prus.justweatherapp.domain.locations.repository.UserLocationsRepository
import javax.inject.Inject

class AddUserLocationUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository,
    private val userLocationsRepository: UserLocationsRepository
) {
    suspend operator fun invoke(
        locationId: String
    ): Result<Unit> {
        return locationsRepository.getLocationById(locationId)?.let { location ->
            userLocationsRepository.addUserLocation(location)
            Result.success(Unit)
        } ?: Result.failure(Throwable("Cannot find the location"))
    }
}