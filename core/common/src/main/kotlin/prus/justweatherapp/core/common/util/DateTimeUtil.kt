package prus.justweatherapp.core.common.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toJavaLocalTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.time.Duration

fun LocalDateTime.formatTime(): String {
    return this.time.formatTime()
}

fun LocalTime.formatTime(): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    return formatter.format(this.toJavaLocalTime())
}

fun Duration.formatTime(): String {
    this.toComponents { hours, minutes, _, _ ->
        val hoursString = if (hours < 10) "0${hours}" else hours
        val minutesString = if (minutes < 10) "0${minutes}" else minutes
        return "$hoursString:$minutesString"
    }
}

fun Duration.formatDuration(): String {
    this.toComponents { hours, minutes, _, _ ->
        val hoursString = if (hours < 10) "0${hours}" else hours
        val minutesString = if (minutes < 10) "0${minutes}" else minutes
        return "${hoursString}h ${minutesString}m"
    }
}

fun LocalDateTime.formatDateTime(): String {
    val formatter = DateTimeFormatter.ofPattern("EEE, dd LLLL, HH:mm")
    return formatter.format(this.toJavaLocalDateTime())
}

fun LocalDate.formatHeaderDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd LLL")
    return formatter.format(this.toJavaLocalDate())
}

fun LocalDate.formatDailyDate(): String {
    val formatter = DateTimeFormatter.ofPattern("EE, dd LLL")
    return formatter.format(this.toJavaLocalDate())
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

fun LocalTime.isBetween(left: LocalTime, right: LocalTime): Boolean {
    return if (left < right) {
        this.toSecondOfDay() >= left.toSecondOfDay() &&
                this.toSecondOfDay() <= right.toSecondOfDay()
    } else {
        this.toSecondOfDay() >= left.toSecondOfDay() ||
                this.toSecondOfDay() <= right.toSecondOfDay()
    }
}

fun LocalTime.getPercentageOfTimeBetween(firstTime: LocalTime, secondTime: LocalTime): Float {
    val current = this.toSecondOfDay()
    val first = firstTime.toSecondOfDay()
    val second = secondTime.toSecondOfDay()
    val dayLength = 24 * 60 * 60

    val left = if (this.isBetween(firstTime, secondTime)) first else second
    val right = if (this.isBetween(firstTime, secondTime)) second else first

    return if (current in left..right) {
        (current - left).toFloat() / (right - left) * 100
    } else if (current > left) {
        (current - left).toFloat() / (dayLength - left + right) * 100
    } else {
        (dayLength - left + current).toFloat() / (dayLength - left + right) * 100
    }

}