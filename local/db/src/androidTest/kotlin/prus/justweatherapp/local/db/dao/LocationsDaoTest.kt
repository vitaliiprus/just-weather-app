package prus.justweatherapp.local.db.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import prus.justweatherapp.local.db.AppDatabase
import prus.justweatherapp.local.db.entity.LocationEntity

@RunWith(AndroidJUnit4::class)
@SmallTest
class LocationsDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var dao: LocationsDao

    private val locationsCount = 30
    private val dbLocations = initDbLocations(locationsCount)

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.locationsDao()

        runBlocking {
            dao.insertAll(dbLocations)
        }
    }

    private fun initDbLocations(count: Int): List<LocationEntity> {
        val locations = mutableListOf<LocationEntity>()
        for (i in 1..count) {
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
    fun getAllLocations() = runTest {
        val allLocations = dao.getAllLocations()

        assert(allLocations.containsAll(dbLocations))
    }

    @Test
    fun getLocationsWithOffset() = runTest {
        assertDaoLocationsExist(0, 5)
        assertDaoLocationsExist(5, 10)
        assertDaoLocationsExist(15, 25)
        assertDaoLocationsExist(3, 21)
        assertDaoLocationsExist(0, 30)
        assertDaoLocationsExist(1, 1)

        assert(dao.getLocations(0, 0).isEmpty())
    }

    private suspend fun assertDaoLocationsExist(offset: Int, limit: Int) {
        assert(dao.getLocations(offset, limit).containsAll(dbLocations.subList(offset, limit)))
    }

    @Test
    fun getLocationsWithMask() = runTest {
        assert(dao.getLocationsWithMask("%ity%").size == locationsCount)
        assert(dao.getLocationsWithMask("%Country%").size == locationsCount)
        assert(dao.getLocationsWithMask("%Admi%").size == locationsCount)
        assert(dao.getLocationsWithMask("%30%").size == 1)
        assert(dao.getLocationsWithMask("%${locationsCount + 1}%").isEmpty())
    }

    @Test
    fun getLocationById() = runTest {
        val location1 = dao.getLocationById("id_1")
        val location2 = dao.getLocationById("id_2")

        assert(location1?.locationId == "id_1")
        assert(location2?.locationId == "id_2")
    }
}