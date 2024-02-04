package prus.justweatherapp.data.locations.testdoubles

import prus.justweatherapp.local.db.dao.LocationsDao
import prus.justweatherapp.local.db.entity.LocationEntity

class TestLocationsDao : LocationsDao {

    private val locations = mutableListOf<LocationEntity>()

    init {
        initLocations()
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

    override suspend fun getAllLocations(): List<LocationEntity> {
        return locations
    }

    override suspend fun getLocations(offset: Int, limit: Int): List<LocationEntity> {
        if (offset + limit > locations.size - 1)
            return listOf()
        return locations.subList(offset, offset + limit)
    }

    override suspend fun getLocationsWithMask(mask: String): List<LocationEntity> {
        val result = mutableListOf<LocationEntity>()
        locations.forEach { location ->
            val isLocationSatisfyMask = location.city.contains(mask, true) ||
                    (location.adminName ?: "").contains(mask, true) ||
                    (location.country ?: "").contains(mask, true)
            if (isLocationSatisfyMask)
                result.add(location)
        }
        return result
    }

    override suspend fun getLocationById(locationId: String): LocationEntity? {
        locations.forEach { location ->
            if (location.locationId == locationId)
                return location
        }
        return null
    }

    override suspend fun insertAll(locationEntities: List<LocationEntity>) {
        locations.addAll(locationEntities)
    }
}