package prus.justweatherapp.data.locations.repository

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
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
    @Ignore("to be implemented")
    fun locationsRepositoryImpl_getLocations() = runTest {
//        assertTrue(isGetLocationsResultSame(0, 30))
//        assertTrue(isGetLocationsResultSame(5, 1))
//        assertTrue(isGetLocationsResultSame(15, 15))
//        assertTrue(isGetLocationsResultSame(30, 1))
//        assertTrue(isGetLocationsResultSame(0, 0))
    }

//    private suspend fun isGetLocationsResultSame(): Boolean {
//        val take = 5
//
//        val pager = TestPager(
//            config = PagingConfig(take),
//            pagingSource = locationsDao.getLocations()
//        )
//        val daoLocations = (pager.refresh() as PagingSource.LoadResult.Page).data
//
//
//        val repositoryLocations = repository.getLocations("")
//
//        return locationsFromDaoAndRepositoryAreSame(daoLocations, repositoryLocations)
//    }

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
            if (daoLocations[i].id != repositoryLocations[i].id)
                return false
        }
        return true
    }

}