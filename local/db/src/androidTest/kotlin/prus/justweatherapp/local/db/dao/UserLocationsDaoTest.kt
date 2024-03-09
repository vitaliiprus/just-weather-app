package prus.justweatherapp.local.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import prus.justweatherapp.local.db.AppDatabase
import prus.justweatherapp.local.db.entity.LocationEntity
import prus.justweatherapp.local.db.entity.UserLocationEntity

@RunWith(AndroidJUnit4::class)
@SmallTest
class UserLocationsDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var userLocationsDao: UserLocationsDao
    private lateinit var locationsDao: LocationsDao

    private val dbLocations = initDbLocations()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = AppDatabase::class.java
        ).allowMainThreadQueries().build()

        userLocationsDao = database.userLocationsDao()
        locationsDao = database.locationsDao()

        runBlocking {
            locationsDao.insertAll(dbLocations)
        }
    }

    private fun initDbLocations(): List<LocationEntity> {
        val locations = mutableListOf<LocationEntity>()
        for (i in 1..2) {
            locations.add(
                LocationEntity(
                    id = "id_$i",
                    city = "City$i",
                    adminName = "Admin$i",
                    country = "Country$i",
                )
            )
        }
        return locations
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun addUserLocation() = runTest {
        val userLocation1 = UserLocationEntity(
            locationId = "id_1",
            displayName = "DisplayName1",
            orderIndex = 0
        )
        val userLocation2 = UserLocationEntity(
            locationId = "id_2",
            displayName = "DisplayName2",
            orderIndex = 1
        )

        userLocationsDao.addUserLocation(userLocation1)
        userLocationsDao.addUserLocation(userLocation2)

        val userLocations = userLocationsDao.getUserLocations().first()

        assert(userLocations.size == 2)
    }

    @Test
    fun updateUserLocationDisplayName() = runTest {

        val userLocation1 = UserLocationEntity(
            locationId = "id_1",
            displayName = "DisplayName1",
            orderIndex = 0
        )
        val userLocation2 = UserLocationEntity(
            locationId = "id_2",
            displayName = "DisplayName2",
            orderIndex = 1
        )

        userLocationsDao.addUserLocation(userLocation1)
        userLocationsDao.addUserLocation(userLocation2)

        userLocationsDao.updateUserLocationDisplayName("id_2", "new")

        assert(
            userLocationsDao.getUserLocationById("id_2")!!.displayName == "new"
                    && userLocationsDao.getUserLocationById("id_1")!!.displayName == "DisplayName1"
        )
    }

    @Test
    fun updateUserLocationOrderIndex() = runTest {
        val userLocation1 = UserLocationEntity(
            locationId = "id_1",
            displayName = "DisplayName1",
            orderIndex = 0
        )
        userLocationsDao.addUserLocation(userLocation1)
        userLocationsDao.updateUserLocationOrderIndex("id_1", 1)

        assert(userLocationsDao.getUserLocationById("id_1")!!.orderIndex == 1)
    }

    @Test
    fun updateUserLocationsOrderIndices() = runTest {
        val userLocation1 = UserLocationEntity(
            locationId = "id_1",
            displayName = "DisplayName1",
            orderIndex = 0
        )

        val userLocation2 = UserLocationEntity(
            locationId = "id_2",
            displayName = "DisplayName2",
            orderIndex = 1
        )

        userLocationsDao.addUserLocation(userLocation1)
        userLocationsDao.addUserLocation(userLocation2)

        userLocationsDao.updateUserLocationsOrderIndices(
            listOf(
                Pair(userLocation1.locationId, userLocation2.orderIndex),
                Pair(userLocation2.locationId, userLocation1.orderIndex),
            )
        )

        assert(
            userLocationsDao.getUserLocationById("id_1")!!.orderIndex == 1
                    && userLocationsDao.getUserLocationById("id_2")!!.orderIndex == 0
        )
    }

    @Test
    fun getUserLocationsCount() = runTest {
        val location1 = locationsDao.getLocationById("id_1")
        val location2 = locationsDao.getLocationById("id_2")

        val userLocation1 = UserLocationEntity(
            locationId = location1!!.id,
            displayName = "",
            orderIndex = 0
        )

        val userLocation2 = UserLocationEntity(
            locationId = location2!!.id,
            displayName = "",
            orderIndex = 1
        )

        assert(userLocationsDao.getUserLocationsCount() == 0)
        userLocationsDao.addUserLocation(userLocation1)
        assert(userLocationsDao.getUserLocationsCount() == 1)
        userLocationsDao.addUserLocation(userLocation2)
        assert(userLocationsDao.getUserLocationsCount() == 2)
    }

    @Test
    fun getUserLocationById() = runTest {
        val location = locationsDao.getLocationById("id_1")

        val userLocation = UserLocationEntity(
            locationId = location!!.id,
            displayName = "",
            orderIndex = 0
        )

        userLocationsDao.addUserLocation(userLocation)

        val userLocationFromDb = userLocationsDao.getUserLocationById(userLocation.locationId)
        assert(userLocation.locationId == userLocationFromDb!!.locationId)
    }

    @Test
    fun deleteUserLocation() = runTest {
        val location = locationsDao.getLocationById("id_1")

        val userLocation = UserLocationEntity(
            locationId = location!!.id,
            displayName = "",
            orderIndex = 0
        )

        assert(userLocationsDao.getUserLocationsCount() == 0)
        userLocationsDao.addUserLocation(userLocation)
        assert(userLocationsDao.getUserLocationsCount() == 1)
        userLocationsDao.getUserLocationById(userLocation.locationId)?.let {
            userLocationsDao.deleteUserLocation(it.locationId)
        }
        assert(userLocationsDao.getUserLocationsCount() == 0)
    }
}