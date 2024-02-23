package prus.justweatherapp.domain.locations.usecase

import prus.justweatherapp.domain.locations.repository.UserLocationsRepository
import javax.inject.Inject

class RestoreUserLocationDisplayNameUseCase @Inject constructor(
    private val userLocationsRepository: UserLocationsRepository
) {
    suspend operator fun invoke(
        locationId: String
    ) {
        userLocationsRepository.getUserLocationById(locationId)?.let {
            userLocationsRepository.updateUserLocationDisplayName(locationId, it.city)
        }
    }
}