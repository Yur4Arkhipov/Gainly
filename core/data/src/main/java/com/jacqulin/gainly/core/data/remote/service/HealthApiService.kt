package com.jacqulin.gainly.core.data.remote.service

import retrofit2.http.GET

interface HealthApiService {

    @GET("health/ping")
    suspend fun pingServer()
}