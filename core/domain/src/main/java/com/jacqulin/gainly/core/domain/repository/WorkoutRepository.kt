package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.core.domain.model.workout.WorkoutById
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import com.jacqulin.gainly.core.util.errors.WorkoutError

interface WorkoutRepository {
    suspend fun createWorkout(
        accessToken: String,
        workout: Workout
    ): Result<WorkoutId, WorkoutError>

    suspend fun getWorkoutHistory(
        accessToken: String,
        from: String,
        to: String,
        last: Int
    ): Result<List<Workout>, WorkoutError>

    suspend fun getFriendWorkoutHistory(
        accessToken: String,
        username: String,
        from: String,
        to: String,
        last: Int
    ): Result<List<Workout>, AppError>

    suspend fun getWorkoutById(
        accessToken: String,
        workoutId: String
    ): Result<WorkoutById, WorkoutError>
}
