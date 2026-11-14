package com.jacqulin.gainly.core.data.remote.service

import com.jacqulin.gainly.core.data.remote.dto.workout.CreateWorkoutRequestDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutIdDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutListItemDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutByIdDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WorkoutApiService {

    /**
     * Создание тренировки
     */
    @POST("workout/api/Workout/create")
    suspend fun createWorkout(
        @Header("Authorization") accessToken: String,
        @Body request: CreateWorkoutRequestDto
    ): WorkoutIdDto

    /**
     * История тренировок самого пользователя
     */
    @GET("workout/api/Workout/workouList")
    suspend fun getWorkoutHistory(
        @Header("Authorization") accessToken: String,
        @Query("From") from: String,
        @Query("To") to: String,
        @Query("Last") last: Int
    ): List<WorkoutListItemDto>

    /**
     * История тренировок друга
     */
    @GET("workout/api/Workout/workouList/{friendsname}")
    suspend fun getFriendWorkoutHistory(
        @Header("Authorization") accessToken: String,
        @Path("friendsname") friendsname: String,
        @Query("From") from: String,
        @Query("To") to: String,
        @Query("Last") last: Int
    ): List<WorkoutListItemDto>

    /**
     * Получение информации о тренировке по id тренировки
     */
    @GET("workout/api/Workout/{workoutId}")
    suspend fun getWorkoutById(
        @Header("Authorization") accessToken: String,
        @Path("workoutId") workoutId: String
    ): WorkoutByIdDto
}