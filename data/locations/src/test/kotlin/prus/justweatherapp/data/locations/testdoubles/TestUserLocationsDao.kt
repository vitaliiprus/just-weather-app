package prus.justweatherapp.data.locations.testdoubles

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
                    id = i + 1,
                    locationId = "id_$i",
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

    override suspend fun getUserLocationsCount(): Int {
        return userLocations.size
    }

    override suspend fun getUserLocations(): List<UserLocationDbModel> {
        val result = mutableListOf<UserLocationDbModel>()
        userLocations.forEachIndexed { index, userLocation ->
            val location = locations[index]
            result.add(
                UserLocationDbModel(
                    userLocation.locationId,
                    location.city,
                    location.adminName ?: "",
                    location.country ?: "",
                    userLocation.displayName ?: "",
                    userLocation.orderIndex,
                    location.lng,
                    location.lat
                )
            )
        }
        return result
    }

    override suspend fun getUserLocationById(locationId: String): UserLocationEntity? {
        userLocations.forEach { location ->
            if (location.locationId == locationId)
                return location
        }
        return null
    }

    override suspend fun deleteUserLocation(userLocation: UserLocationEntity) {
        userLocations.remove(userLocation)
    }
}