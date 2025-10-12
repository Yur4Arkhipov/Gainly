package com.jacqulin.gainly.startup

import com.jacqulin.gainly.core.data.remote.service.HealthApiService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface HealthApiEntryPoint {
    fun healthApiService(): HealthApiService
}