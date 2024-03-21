package prus.justweatherapp.core.common.util

import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class TimeUpdater(
    private val timezoneOffset: Int,
    private val onTimeUpdated: (LocalDateTime) -> Unit
) {

    private var timer: CoroutineTimer? = null

    init {
        val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val initialDelay = (60_000_000_000L - (currentTime.second * 1_000_000_000L + currentTime.nanosecond)) / 1_000_000
        updateTime()
        startTimeUpdater(initialDelay)
    }

    private fun startTimeUpdater(delay: Long) {
        timer?.cancel()
        timer = CoroutineTimer(delay) {
            updateTime()
            startTimeUpdater(60_000)
        }
    }

    private fun updateTime() {
        onTimeUpdated.invoke(getLocationCurrentTime(timezoneOffset))
    }

    fun cancel() {
        timer?.cancel()
    }
}