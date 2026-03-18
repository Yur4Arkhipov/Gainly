package com.jacqulin.gainly.core.data.usecase.workout

import com.jacqulin.gainly.core.domain.model.workout.WorkoutData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.domain.usecase.workout.CreateWorkoutUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError
import javax.inject.Inject

class CreateWorkoutUseCaseImpl @Inject constructor(
    private val workoutRepository: WorkoutRepository
) : CreateWorkoutUseCase {
    override suspend operator fun invoke(
        accessToken: String,
        workout: WorkoutData
    ): Result<WorkoutId, WorkoutError> {
        return workoutRepository.createWorkout(accessToken, workout)
    }
}