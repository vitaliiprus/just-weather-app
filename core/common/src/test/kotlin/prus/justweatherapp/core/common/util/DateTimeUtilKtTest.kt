package prus.justweatherapp.core.common.util

import kotlinx.datetime.LocalTime
import org.junit.Test
import kotlin.math.roundToInt

class DateTimeUtilKtTest {

    private val Int.hoursToSeconds: Int
        get() =
            this * 60 * 60
    private val Float.hoursToSeconds: Int
        get() =
            (this * 60 * 60).roundToInt()

    private fun getLocalTime(hours: Int): LocalTime {
        return LocalTime.fromSecondOfDay(hours.hoursToSeconds)
    }

    private fun getLocalTime(hours: Float): LocalTime {
        return LocalTime.fromSecondOfDay(hours.hoursToSeconds)
    }

    @Test
    fun isBetween() {
        val left1 = getLocalTime(6)
        val right1 = getLocalTime(18)

        setOf(
            Pair(getLocalTime(9), true),
            Pair(getLocalTime(17), true),
            Pair(getLocalTime(19), false),
            Pair(getLocalTime(0), false),
        ).forEach {
            assert(it.first.isBetween(left1, right1) == it.second) {
                "Failed set1 at ${it.first} is ${it.second}"
            }
        }

        //sunset at 1:00, sunrise at 2:00 (possible in areas, close to the polar regions)
        val left2 = getLocalTime(2)
        val right2 = getLocalTime(1)

        setOf(
            Pair(getLocalTime(9), true),
            Pair(getLocalTime(17), true),
            Pair(getLocalTime(19), true),
            Pair(getLocalTime(0), true),
            Pair(getLocalTime(1.5f), false),
        ).forEach {
            assert(it.first.isBetween(left2, right2) == it.second) {
                "Failed set2 at ${it.first} is ${it.second}"
            }
        }
    }

    @Test
    fun getPercentageOfTimeBetween() {
        val left1 = getLocalTime(6)
        val right1 = getLocalTime(18)

        setOf(
            Pair(getLocalTime(9), 3f / 12 * 100),
            Pair(getLocalTime(17), 11f / 12 * 100),
            Pair(getLocalTime(19), 1f / 12 * 100),
            Pair(getLocalTime(0), 6f / 12 * 100),
            Pair(getLocalTime(6), 0f / 12 * 100),
            Pair(getLocalTime(18), 12f / 12 * 100),
        ).forEach {
            assert(it.first.getPercentageOfTimeBetween(left1, right1) == it.second) {
                "Failed set1: ${it.first} should be ${it.second} (actual is ${it.first.getPercentageOfTimeBetween(left1, right1)})"
            }
        }

        //sunset at 1:00, sunrise at 2:00 (possible in areas, close to the polar regions)
        val left2 = getLocalTime(2)
        val right2 = getLocalTime(1)

        setOf(
            Pair(getLocalTime(9), 7f / 23 * 100),
            Pair(getLocalTime(17), 15f / 23 * 100),
            Pair(getLocalTime(19), 17f / 23 * 100),
            Pair(getLocalTime(0), 22f / 23 * 100),
            Pair(getLocalTime(1.5f), 0.5f / 1 * 100),
        ).forEach {
            assert(it.first.getPercentageOfTimeBetween(left2, right2) == it.second) {
                "Failed set2: ${it.first} should be ${it.second} (actual is ${it.first.getPercentageOfTimeBetween(left1, right1)})"
            }
        }
    }
}