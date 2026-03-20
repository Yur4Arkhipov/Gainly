package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.workout.WorkoutById
import com.jacqulin.gainly.core.domain.model.workout.WorkoutData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError

interface WorkoutRepository {
    suspend fun createWorkout(
        accessToken: String,
        workout: WorkoutData
    ): Result<WorkoutId, WorkoutError>

    suspend fun getWorkoutHistory(
        accessToken: String,
        from: String,
        to: String,
        last: Int
    ): Result<List<WorkoutListItem>, WorkoutError>

    suspend fun getFriendWorkoutHistory(
        accessToken: String,
        friendsname: String,
        from: String,
        to: String,
        last: Int
    ): Result<List<WorkoutListItem>, WorkoutError>

    suspend fun getWorkoutById(
        accessToken: String,
        workoutId: String
    ): Result<WorkoutById, WorkoutError>
}