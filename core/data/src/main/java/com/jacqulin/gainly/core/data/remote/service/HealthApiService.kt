package com.jacqulin.gainly.core.data.remote.service

import retrofit2.http.GET

/**
 * API service to wake up the server
 *
 * Used to send a lightweight request to the server to wake up it
 */
interface HealthApiService {

    /**
     * Sends a GET request to the `/health/ping` endpoint.
     *
     * This method does not return any data and requires no authentication.
     * A successful response (HTTP 200 OK) indicates that the server is up and ready to handle requests.
     */
    @GET("health/ping")
    suspend fun pingServer()
}