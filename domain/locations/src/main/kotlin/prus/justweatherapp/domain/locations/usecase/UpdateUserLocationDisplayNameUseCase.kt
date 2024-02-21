package prus.justweatherapp.domain.locations.usecase

import prus.justweatherapp.domain.locations.repository.UserLocationsRepository
import javax.inject.Inject

class UpdateUserLocationDisplayNameUseCase @Inject constructor(
    private val userLocationsRepository: UserLocationsRepository
) {
    suspend operator fun invoke(
        locationId: String,
        newDisplayName: String,
    ) {
        userLocationsRepository.updateUserLocationDisplayName(locationId, newDisplayName)
    }
}