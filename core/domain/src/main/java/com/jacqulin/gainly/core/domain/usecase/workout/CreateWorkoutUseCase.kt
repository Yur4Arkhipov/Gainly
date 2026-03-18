package com.jacqulin.gainly.core.domain.usecase.workout

import com.jacqulin.gainly.core.domain.model.workout.WorkoutData

interface CreateWorkoutUseCase {
    suspend operator fun invoke(workout: WorkoutData)
}