package prus.justweatherapp.core.common.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatTime():String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return formatter.format(this.toJavaLocalDateTime())
}

fun getLocationCurrentTime(timezoneOffset: Int): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.UTC)
        .toInstant(UtcOffset.ZERO)
        .plus(timezoneOffset, DateTimeUnit.SECOND)
        .toLocalDateTime(TimeZone.UTC)
}