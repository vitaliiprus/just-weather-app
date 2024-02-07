package prus.justweatherapp.data.locations.repository

import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import prus.justweatherapp.data.locations.mapper.mapToDomainModels
import prus.justweatherapp.data.locations.testdoubles.TestUserLocationsDao
import prus.justweatherapp.domain.locations.model.Location

class UserLocationsRepositoryImplTest {

    private lateinit var repository: UserLocationsRepositoryImpl

    private lateinit var userLocationsDao: TestUserLocationsDao

    @Before
    fun setup() {
        userLocationsDao = TestUserLocationsDao()
        repository = UserLocationsRepositoryImpl(userLocationsDao)
    }

    @Test
    fun userLocationsRepositoryImpl_addUserLocations() = runTest {
        val countBefore = userLocationsDao.getUserLocationsCount()
        repository.addUserLocation(
            Location(
                id = "id_6",
                city = "City6",
                orderIndex = 5,
                lng = 0.0,
                lat = 0.0
            )
        )
        val countAfter = userLocationsDao.getUserLocationsCount()

        assertTrue(countAfter - countBefore == 1)
    }

    @Test
    fun userLocationsRepositoryImpl_getUserLocations() = runTest {
        val daoLocations = userLocationsDao.getUserLocations().first().mapToDomainModels()
        val repositoryLocations = repository.getUserLocations().first()

        assertTrue(locationsFromDaoAndRepositoryAreSame(daoLocations, repositoryLocations))
    }

    @Test
    fun userLocationsRepositoryImpl_deleteUserLocation() = runTest {
        val countBefore = userLocationsDao.getUserLocationsCount()
        val userLocation = repository.getUserLocations().first().last()
        repository.deleteUserLocation(userLocation)
        val countAfter = userLocationsDao.getUserLocationsCount()

        assertTrue(countBefore - countAfter == 1)
    }

    private fun locationsFromDaoAndRepositoryAreSame(
        daoLocations: List<Location>,
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