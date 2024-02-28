package prus.justweatherapp.core.common.format

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatTime():String {
    val formatter = DateTimeFormatter.ofPattern("hh:mm")
    return formatter.format(this.toJavaLocalDateTime())
}