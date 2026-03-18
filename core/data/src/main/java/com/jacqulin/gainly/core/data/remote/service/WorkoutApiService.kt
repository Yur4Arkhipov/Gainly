package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface WorkoutApiService {

    @POST("workout/api/Workout/create")
    suspend fun createWorkout(
        @Body request: WorkoutRequestDto
    ): Unit
}