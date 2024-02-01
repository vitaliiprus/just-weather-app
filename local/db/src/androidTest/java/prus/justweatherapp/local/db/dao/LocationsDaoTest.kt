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
    private lateinit var dao: LocationDao

    private val dbLocations = listOf(
        LocationEntity(
            id = "1",
            locationId = "id_1",
            city = "City1",
        ),
        LocationEntity(
            id = "2",
            locationId = "id_2",
            city = "City2",
        )
    )

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = AppDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.locationDao()

        runBlocking {
            dao.insertAll(dbLocations)
        }
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
    fun getLocationById() = runTest {
        val location1 = dao.getLocationById("id_1")
        val location2 = dao.getLocationById("id_2")
        assert(location1.locationId == "id_1")
        assert(location2.locationId == "id_2")
    }
}