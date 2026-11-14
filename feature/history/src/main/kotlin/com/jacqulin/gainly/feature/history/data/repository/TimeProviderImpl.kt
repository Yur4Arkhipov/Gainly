package com.jacqulin.gainly.feature.history.data.repository

import com.jacqulin.gainly.feature.history.domain.repository.TimeProvider
import java.time.LocalDate
import javax.inject.Inject

class TimeProviderImpl @Inject constructor() : TimeProvider {
    override fun now(): LocalDate = LocalDate.now()
}
