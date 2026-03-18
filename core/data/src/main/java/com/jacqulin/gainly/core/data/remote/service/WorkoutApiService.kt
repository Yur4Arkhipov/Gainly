package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutRequestDto
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface WorkoutApiService {

    @POST("workout/api/Workout/create")
    suspend fun createWorkout(
        @Header("Authorization") accessToken: String,
        @Body request: WorkoutRequestDto
    ): WorkoutId
}