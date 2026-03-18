package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.workout.WorkoutData
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError

interface WorkoutRepository {
    suspend fun createWorkout(workout: WorkoutData): Result<Unit, WorkoutError>
}