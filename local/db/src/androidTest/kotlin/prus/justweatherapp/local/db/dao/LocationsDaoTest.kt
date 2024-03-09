package prus.justweatherapp.local.db.dao

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.testing.TestPager
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

    private val dbLocations = initDbLocations()

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

    private fun initDbLocations(): List<LocationEntity> {
        val locations = mutableListOf<LocationEntity>()
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
    fun getLocations() = runTest {
        val take = 5

        val pager = TestPager(
            config = PagingConfig(take),
            pagingSource = dao.getLocations()
        )
        val daoLocations = pager.refresh() as PagingSource.LoadResult.Page

        assert(daoLocations.data.containsAll(dbLocations.sortedBy { it.city }.subList(0, take)))
    }

    @Test
    fun getLocationById() = runTest {
        val location1 = dao.getLocationById("id_1")
        val location2 = dao.getLocationById("id_2")

        assert(location1?.id == "id_1")
        assert(location2?.id == "id_2")
    }
}