package prus.justweatherapp.data.locations.testdoubles

import androidx.paging.PagingSource
import androidx.paging.testing.asPagingSourceFactory
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
                    id = "id_$i",
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

    override fun getLocations(query: String): PagingSource<Int, LocationEntity> {
        val result = mutableListOf<LocationEntity>()
        locations.forEach { location ->
            val isLocationSatisfyMask = location.city.contains(query, true) ||
                    (location.adminName ?: "").contains(query, true) ||
                    (location.country ?: "").contains(query, true)
            if (isLocationSatisfyMask)
                result.add(location)
        }

        return result.asPagingSourceFactory().invoke()
    }

    override suspend fun getLocationById(locationId: String): LocationEntity? {
        locations.forEach { location ->
            if (location.id == locationId)
                return location
        }
        return null
    }

    override suspend fun insertAll(locationEntities: List<LocationEntity>) {
        locations.addAll(locationEntities)
    }
}