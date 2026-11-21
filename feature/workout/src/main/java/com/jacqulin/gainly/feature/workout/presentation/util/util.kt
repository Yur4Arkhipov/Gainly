package com.jacqulin.gainly.feature.workout.presentation.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

internal val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.uuuu")

internal val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

internal fun String.toDateMillis(): Long {
    val date = LocalDate.parse(this, dateFormatter)

    return date.atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()
}

internal fun String.isValidTimeInput(): Boolean {
    if (length > 4 || any { !it.isDigit() }) return false

    if (isEmpty()) return true

    if (this[0] > '2') return false

    if (length >= 2 && this[0] == '2' && this[1] > '3') {
        return false
    }

    if (length >= 3 && this[2] > '5') return false

    return true
}

internal fun String.isValidTime(): Boolean {
    if (length != 5) return false

    return runCatching {
        LocalTime.parse(this, timeFormatter)
    }.isSuccess
}

internal fun String.toWorkoutDateTime(time: String): String {
    val date = LocalDate.parse(this, dateFormatter)
    val localTime = LocalTime.parse(time, timeFormatter)

    return LocalDateTime.of(date, localTime)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toString()
}
