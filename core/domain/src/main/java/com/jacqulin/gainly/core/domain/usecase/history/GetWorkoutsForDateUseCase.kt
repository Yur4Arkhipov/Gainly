package com.jacqulin.gainly.core.domain.usecase.history

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError
import kotlinx.coroutines.flow.firstOrNull
import java.time.LocalDate
import javax.inject.Inject

class GetWorkoutsForDateUseCase @Inject constructor(
    val repository: WorkoutRepository,
    val tokenStorage: TokenStorage
) {
    suspend operator fun invoke(date: LocalDate): Result<List<Workout>, WorkoutError> {
        val accessToken = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                WorkoutError.LocalError(WorkoutError.Local.TOKEN_NOT_FOUND)
            )

        return repository.getWorkoutHistory(
            accessToken = "Bearer $accessToken",
            from = date.minusDays(0).toString(),
            to = date.plusDays(1).toString(),
            last = 20
        )
    }
}
