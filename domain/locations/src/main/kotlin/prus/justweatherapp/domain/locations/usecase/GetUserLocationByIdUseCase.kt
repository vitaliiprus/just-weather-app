package prus.justweatherapp.domain.locations.usecase

import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.domain.locations.repository.UserLocationsRepository
import javax.inject.Inject

class GetUserLocationByIdUseCase @Inject constructor(
    private val userLocationsRepository: UserLocationsRepository
) {
    suspend operator fun invoke(
        locationId: String
    ): Location? {
        return userLocationsRepository.getUserLocationById(locationId)
    }
}