package com.jacqulin.gainly.core.data.usecase.workout

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.workout.WorkoutById
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.domain.usecase.workout.GetWorkoutByIdUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetWorkoutByIdUseCaseImpl @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val tokenStorage: TokenStorage
) : GetWorkoutByIdUseCase {

    override suspend fun invoke(
        workoutId: String
    ): Result<WorkoutById, WorkoutError> {
        val accessToken = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                WorkoutError.LocalError(WorkoutError.Local.TOKEN_NOT_FOUND)
            )
        return workoutRepository.getWorkoutById(
            accessToken = "Bearer $accessToken",
            workoutId = workoutId
        )
    }
}