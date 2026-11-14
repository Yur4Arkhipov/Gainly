package com.jacqulin.gainly.core.domain.repository

import java.time.LocalDate

interface TimeProvider {
    fun now(): LocalDate
}
