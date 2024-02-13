package prus.justweatherapp.feature.locations.model

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchLocationUiModelTest {

    private lateinit var location: SearchLocationUiModel

    @Before
    fun setUp() {
        location = SearchLocationUiModel(
            id = "1",
            city = "City1",
            adminName = "AdminName1",
            country = "Country1"
        )
    }

    @Test
    fun findOccurrences(): Unit = runTest {
        location.findOccurrences("City")
        assertTrue(location.cityOccurrences == listOf(Pair(0, 3)))
        assertTrue(location.adminNameOccurrences.isEmpty())
        assertTrue(location.countryOccurrences.isEmpty())

        location.findOccurrences("c")
        assertTrue(location.cityOccurrences == listOf(Pair(0, 0)))
        assertTrue(location.adminNameOccurrences.isEmpty())
        assertTrue(location.countryOccurrences == listOf(Pair(0, 0)))

        location.findOccurrences("a")
        assertTrue(location.cityOccurrences.isEmpty())
        assertTrue(location.adminNameOccurrences == listOf(Pair(0, 0), Pair(6, 6)))
        assertTrue(location.countryOccurrences.isEmpty())

        location.findOccurrences("1")
        assertTrue(location.cityOccurrences == listOf(Pair(4, 4)))
        assertTrue(location.adminNameOccurrences == listOf(Pair(9, 9)))
        assertTrue(location.countryOccurrences == listOf(Pair(7, 7)))

        location.findOccurrences("")
        assertTrue(location.cityOccurrences.isEmpty())
        assertTrue(location.adminNameOccurrences.isEmpty())
        assertTrue(location.countryOccurrences.isEmpty())
    }
}