package com.jacqulin.gainly.feature.history.domain.repository

import java.time.LocalDate

interface TimeProvider {
    fun now(): LocalDate
}
