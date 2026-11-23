package com.jacqulin.gainly.core.domain.usecase.history

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DeleteWorkoutByIdUseCase @Inject constructor(
    val repository: WorkoutRepository,
    val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(workoutId: String): Result<Unit, AppError> {
        val accessToken = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                AppError.LocalError(AppError.Local.TOKEN_NOT_FOUND)
            )

        return repository.deleteWorkout(
            accessToken = "Bearer $accessToken",
            workoutId = workoutId
        )
    }
}
