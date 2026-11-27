package com.jacqulin.gainly.core.domain.usecase.friends

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate
import javax.inject.Inject

class GetFriendWorkoutsForDateUseCase @Inject constructor(
    val repository: WorkoutRepository,
    val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(username: String): Result<List<Workout>, AppError> {
        val from = LocalDate.of(2025, 1, 1).toString()
        val to = LocalDate.now().toString()

        val accessToken = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                AppError.LocalError(AppError.Local.TOKEN_NOT_FOUND)
            )

        return repository.getFriendWorkoutHistory(
            accessToken = "Bearer $accessToken",
            username = username,
            from = from,
            to = to,
            last = 10
        )
    }
}
