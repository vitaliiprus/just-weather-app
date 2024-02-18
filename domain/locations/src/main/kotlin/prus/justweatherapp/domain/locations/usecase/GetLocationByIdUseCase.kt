package prus.justweatherapp.domain.locations.usecase

import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.domain.locations.repository.LocationsRepository
import javax.inject.Inject

class GetLocationByIdUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(
        locationId: String
    ): Location? {
        return locationsRepository.getLocationById(locationId)
    }
}