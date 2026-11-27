package com.jacqulin.gainly.core.data.repository

import com.jacqulin.gainly.core.domain.repository.TimeProvider
import java.time.LocalDate
import javax.inject.Inject

class TimeProviderImpl @Inject constructor() : TimeProvider {
    override fun now(): LocalDate = LocalDate.now()
}
