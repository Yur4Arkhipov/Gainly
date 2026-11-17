package com.jacqulin.gainly.core.data.usecase.workout

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.domain.usecase.workout.CreateWorkoutUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class CreateWorkoutUseCaseImpl @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val tokenStorage: TokenStorage,
) : CreateWorkoutUseCase {
    override suspend operator fun invoke(workout: Workout): Result<WorkoutId, WorkoutError> {
        val accessToken = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                WorkoutError.LocalError(WorkoutError.Local.TOKEN_NOT_FOUND)
            )

        return workoutRepository.createWorkout(accessToken, workout)
    }
}
