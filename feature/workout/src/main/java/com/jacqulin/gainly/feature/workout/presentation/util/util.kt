package com.jacqulin.gainly.feature.workout.presentation.util

import java.time.LocalDate
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

internal val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

internal fun String.toDateMillis(): Long {
    val date = LocalDate.parse(this, dateFormatter)

    return date.atStartOfDay(ZoneOffset.UTC)
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
