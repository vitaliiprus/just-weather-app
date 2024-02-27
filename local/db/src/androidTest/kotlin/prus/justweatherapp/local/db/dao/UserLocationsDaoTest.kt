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
    private lateinit var dao: UserLocationsDao
    private lateinit var locationsDao: LocationsDao

    private val dbLocations = initDbLocations()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.userLocationsDao()
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
                    id = i + 1,
                    locationId = "id_$i",
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

        dao.addUserLocation(userLocation1)
        dao.addUserLocation(userLocation2)

        val userLocations = dao.getUserLocations().first()

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

        dao.addUserLocation(userLocation1)
        dao.addUserLocation(userLocation2)

        dao.updateUserLocationDisplayName("id_2", "new")

        assert(
            dao.getUserLocationById("id_2")!!.displayName == "new"
                    && dao.getUserLocationById("id_1")!!.displayName == "DisplayName1"
        )
    }

    @Test
    fun updateUserLocationOrderIndex() = runTest {
        val userLocation1 = UserLocationEntity(
            locationId = "id_1",
            displayName = "DisplayName1",
            orderIndex = 0
        )
        dao.addUserLocation(userLocation1)
        dao.updateUserLocationOrderIndex("id_1", 1)

        assert(dao.getUserLocationById("id_1")!!.orderIndex == 1)
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

        dao.addUserLocation(userLocation1)
        dao.addUserLocation(userLocation2)

        dao.updateUserLocationsOrderIndices(
            listOf(
                Pair(userLocation1.locationId, userLocation2.orderIndex),
                Pair(userLocation2.locationId, userLocation1.orderIndex),
            )
        )

        assert(
            dao.getUserLocationById("id_1")!!.orderIndex == 1
                    && dao.getUserLocationById("id_2")!!.orderIndex == 0
        )
    }

    @Test
    fun getUserLocationsCount() = runTest {
        val location1 = locationsDao.getLocationById("id_1")
        val location2 = locationsDao.getLocationById("id_2")

        val userLocation1 = UserLocationEntity(
            locationId = location1!!.locationId,
            displayName = "",
            orderIndex = 0
        )

        val userLocation2 = UserLocationEntity(
            locationId = location2!!.locationId,
            displayName = "",
            orderIndex = 1
        )

        assert(dao.getUserLocationsCount() == 0)
        dao.addUserLocation(userLocation1)
        assert(dao.getUserLocationsCount() == 1)
        dao.addUserLocation(userLocation2)
        assert(dao.getUserLocationsCount() == 2)
    }

    @Test
    fun getUserLocationById() = runTest {
        val location = locationsDao.getLocationById("id_1")

        val userLocation = UserLocationEntity(
            locationId = location!!.locationId,
            displayName = "",
            orderIndex = 0
        )

        dao.addUserLocation(userLocation)

        val userLocationFromDb = dao.getUserLocationById(userLocation.locationId)
        assert(userLocation.locationId == userLocationFromDb!!.locationId)
    }

    @Test
    fun deleteUserLocation() = runTest {
        val location = locationsDao.getLocationById("id_1")

        val userLocation = UserLocationEntity(
            locationId = location!!.locationId,
            displayName = "",
            orderIndex = 0
        )

        assert(dao.getUserLocationsCount() == 0)
        dao.addUserLocation(userLocation)
        assert(dao.getUserLocationsCount() == 1)
        dao.getUserLocationById(userLocation.locationId)?.let {
            dao.deleteUserLocation(it.locationId)
        }
        assert(dao.getUserLocationsCount() == 0)
    }
}