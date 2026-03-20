package com.jacqulin.gainly.core.domain.usecase.workout

import com.jacqulin.gainly.core.domain.model.workout.WorkoutById
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError

interface GetWorkoutByIdUseCase {
    suspend operator fun invoke(workoutId: String): Result<WorkoutById, WorkoutError>
}