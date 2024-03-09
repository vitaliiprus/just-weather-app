package prus.justweatherapp.data.locations.testdoubles

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import prus.justweatherapp.local.db.dao.UserLocationsDao
import prus.justweatherapp.local.db.entity.LocationEntity
import prus.justweatherapp.local.db.entity.UserLocationEntity
import prus.justweatherapp.local.db.model.UserLocationDbModel

class TestUserLocationsDao : UserLocationsDao {

    private val locations = mutableListOf<LocationEntity>()
    private val userLocations = mutableListOf<UserLocationEntity>()

    init {
        initLocations()
        initUserLocations()
    }

    private fun initLocations() {
        locations.clear()
        for (i in 1..30) {
            locations.add(
                LocationEntity(
                    id = "id_$i",
                    city = "City$i",
                    adminName = "Admin$i",
                    country = "Country$i",
                )
            )
        }
    }

    private fun initUserLocations() {
        userLocations.clear()
        for (i in 1..5) {
            userLocations.add(
                UserLocationEntity(
                    locationId = "id_$i",
                    displayName = "",
                    orderIndex = i - 1
                )
            )
        }
    }

    override suspend fun addUserLocation(userLocation: UserLocationEntity) {
        userLocations.add(userLocation)
    }

    override suspend fun updateUserLocationDisplayName(locationId: String, newDisplayName: String) {
        userLocations.find { it.locationId == locationId }?.let { userLocation ->
            val newUserLocation = userLocation.copy(
                displayName = newDisplayName
            )
            val index = userLocations.indexOf(userLocation)
            userLocations.remove(userLocation)
            userLocations.add(index, newUserLocation)
        }
    }

    override fun updateUserLocationOrderIndex(locationId: String, orderIndex: Int) {
        userLocations.find { it.locationId == locationId }?.let { userLocation ->
            userLocation.orderIndex = orderIndex
        }
    }

    override suspend fun getUserLocationsCount(): Int {
        return userLocations.size
    }

    override fun getUserLocations(): Flow<List<UserLocationDbModel>> = flow {
        val result = mutableListOf<UserLocationDbModel>()
        userLocations.forEachIndexed { index, userLocation ->
            val location = locations[index]
            result.add(
                UserLocationDbModel(
                    userLocation.locationId,
                    location.city,
                    location.adminName ?: "",
                    location.country ?: "",
                    userLocation.displayName,
                    userLocation.orderIndex,
                    location.lng,
                    location.lat
                )
            )
        }
        emit(result)
    }

    override suspend fun getUserLocationById(locationId: String): UserLocationDbModel? {
        locations.find { it.id == locationId }?.let { location ->
            userLocations.find { it.locationId == locationId }?.let { userLocation ->
                return UserLocationDbModel(
                    userLocation.locationId,
                    location.city,
                    location.adminName ?: "",
                    location.country ?: "",
                    userLocation.displayName,
                    userLocation.orderIndex,
                    location.lng,
                    location.lat
                )
            }
        }
        return null
    }

    override suspend fun deleteUserLocation(locationId: String) {
        userLocations.remove(userLocations.find { it.locationId == locationId })
    }
}