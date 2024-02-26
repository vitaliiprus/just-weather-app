package prus.justweatherapp.domain.locations.usecase

import kotlinx.coroutines.flow.first
import prus.justweatherapp.domain.locations.repository.UserLocationsRepository
import javax.inject.Inject

class UpdateUserLocationOrderIndexUseCase @Inject constructor(
    private val userLocationsRepository: UserLocationsRepository
) {
    suspend operator fun invoke(
        fromLocationId: String,
        toLocationId: String,
    ) {
        val locations = userLocationsRepository.getUserLocations().first()

        locations.find { it.id == fromLocationId }?.let { fromLocation ->
            locations.find { it.id == toLocationId }?.let { toLocation ->
                val fromOrderIndex = fromLocation.orderIndex!!
                val toOrderIndex = toLocation.orderIndex!!

                locations.map {
                    it.orderIndex =
                        if (it.orderIndex == fromOrderIndex)
                            toOrderIndex
                        else if (
                            fromOrderIndex > toOrderIndex
                            && it.orderIndex!! >= toOrderIndex
                            && it.orderIndex!! < fromOrderIndex
                        )
                            (it.orderIndex!!) + 1
                        else if (
                            fromOrderIndex < toOrderIndex
                            && it.orderIndex!! <= toOrderIndex
                            && it.orderIndex!! > fromOrderIndex
                        )
                            (it.orderIndex!!) - 1
                        else
                            it.orderIndex
                }

                userLocationsRepository.updateUserLocationsOrderIndices(
                    locations.map {
                        it.id to it.orderIndex!!
                    }
                )
            }
        }
    }

}