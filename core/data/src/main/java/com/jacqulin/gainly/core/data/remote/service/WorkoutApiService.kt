package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutListResponseDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutRequestDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutResponseDto
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkoutApiService {

    @POST("workout/api/Workout/create")
    suspend fun createWorkout(
        @Header("Authorization") accessToken: String,
        @Body request: WorkoutRequestDto
    ): WorkoutId

    @GET("workout/api/Workout/workouList")
    suspend fun getWorkoutHistory(
        @Header("Authorization") accessToken: String,
        @Query("From") from: String,
        @Query("To") to: String,
        @Query("Last") last: Int
    ): WorkoutListResponseDto

    @GET("workout/api/Workout/{workoutId}")
    suspend fun getWorkoutById(
        @Header("Authorization") accessToken: String,
        @Path("workoutId") workoutId: String
    ): WorkoutResponseDto
}