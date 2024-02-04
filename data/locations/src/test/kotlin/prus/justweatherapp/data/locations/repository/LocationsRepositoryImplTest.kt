package prus.justweatherapp.data.locations.repository

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import prus.justweatherapp.data.locations.testdoubles.TestLocationsDao
import prus.justweatherapp.domain.locations.model.Location
import prus.justweatherapp.local.db.entity.LocationEntity

class LocationsRepositoryImplTest {

    private lateinit var repository: LocationsRepositoryImpl

    private lateinit var locationsDao: TestLocationsDao

    @Before
    fun setup() {
        locationsDao = TestLocationsDao()
        repository = LocationsRepositoryImpl(locationsDao)
    }

    @Test
    fun locationsRepositoryImpl_getLocations() = runTest {
        assertTrue(isGetLocationsResultSame(0, 30))
        assertTrue(isGetLocationsResultSame(5, 1))
        assertTrue(isGetLocationsResultSame(15, 15))
        assertTrue(isGetLocationsResultSame(30, 1))
        assertTrue(isGetLocationsResultSame(0, 0))
    }

    private suspend fun isGetLocationsResultSame(skip: Int, take: Int): Boolean {
        val daoLocations = locationsDao.getLocations(skip, take)
        val repositoryLocations = repository.getLocations(skip, take)

        return locationsFromDaoAndRepositoryAreSame(daoLocations, repositoryLocations)
    }

    @Test
    fun locationsRepositoryImpl_getLocationsWithMask() = runTest {
        assertTrue(isGetLocationsWithMaskResultSame("City"))
        assertTrue(isGetLocationsWithMaskResultSame("Country"))
        assertTrue(isGetLocationsWithMaskResultSame("Admin"))
        assertTrue(isGetLocationsWithMaskResultSame("1"))
        assertTrue(isGetLocationsWithMaskResultSame("12"))
        assertTrue(isGetLocationsWithMaskResultSame("-1"))
    }

    private suspend fun isGetLocationsWithMaskResultSame(mask: String): Boolean {
        val daoLocations = locationsDao.getLocationsWithMask(mask)
        val repositoryLocations = repository.getLocationsWithMask(mask)

        return locationsFromDaoAndRepositoryAreSame(daoLocations, repositoryLocations)
    }

    @Test
    fun locationsRepositoryImpl_getLocationById() = runTest {
        assertTrue(isGetLocationByIdResultSame("id_1"))
        assertTrue(isGetLocationByIdResultSame("id_30"))
        assertTrue(isGetLocationByIdResultSame("id_31"))
        assertTrue(isGetLocationByIdResultSame("1"))
    }

    private suspend fun isGetLocationByIdResultSame(id: String): Boolean {
        val daoLocation = locationsDao.getLocationById(id)
        val repositoryLocation = repository.getLocationById(id)

        return locationsFromDaoAndRepositoryAreSame(
            listOfNotNull(daoLocation),
            listOfNotNull(repositoryLocation),
        )
    }

    private fun locationsFromDaoAndRepositoryAreSame(
        daoLocations: List<LocationEntity>,
        repositoryLocations: List<Location>
    ): Boolean {
        if (daoLocations.size != repositoryLocations.size)
            return false

        for (i in daoLocations.indices) {
            if (daoLocations[i].locationId != repositoryLocations[i].id)
                return false
        }
        return true
    }

}