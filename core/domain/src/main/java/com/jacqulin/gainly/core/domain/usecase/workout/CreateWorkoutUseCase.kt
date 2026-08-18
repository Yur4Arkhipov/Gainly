package com.jacqulin.gainly.core.domain.usecase.workout

import com.jacqulin.gainly.core.domain.model.workout.WorkoutData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError

interface CreateWorkoutUseCase {
    suspend operator fun invoke(
        accessToken: String,
        workout: WorkoutData
    ): Result<WorkoutId, WorkoutError>
}