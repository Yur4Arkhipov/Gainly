package com.jacqulin.gainly.core.domain.repository

import com.jacqulin.gainly.core.domain.model.workout.WorkoutData

interface WorkoutRepository {
    suspend fun createWorkout(workout: WorkoutData)
}