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
import kotlin.time.Duration

fun LocalDateTime.formatTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return formatter.format(this.toJavaLocalDateTime())
}

fun Duration.formatTime(): String {
    this.toComponents { hours, minutes, _, _ ->
        val hoursString = if(hours < 10) "0${hours}" else hours
        val minutesString = if(minutes < 10) "0${minutes}" else minutes
        return "$hoursString:$minutesString"
    }
}

fun LocalDateTime.formatDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("EEE, dd LLLL, HH:mm")
    return formatter.format(this.toJavaLocalDateTime())
}

fun getLocationCurrentTime(timezoneOffset: Int): LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.UTC)
        .toInstant(UtcOffset.ZERO)
        .plus(timezoneOffset, DateTimeUnit.SECOND)
        .toLocalDateTime(TimeZone.UTC)
}

fun LocalDateTime.addTimezoneOffset(timezoneOffset: Int): LocalDateTime {
    return this
        .toInstant(UtcOffset.ZERO)
        .plus(timezoneOffset, DateTimeUnit.SECOND)
        .toLocalDateTime(TimeZone.UTC)
}