package com.jacqulin.gainly.core.data.usecase.workout

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.domain.usecase.workout.GetWorkoutHistoryUseCase
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetWorkoutHistoryUseCaseImpl @Inject constructor(
    private val workoutRepository: WorkoutRepository,
    private val tokenStorage: TokenStorage
) : GetWorkoutHistoryUseCase {

    override suspend fun invoke(
        from: String,
        to: String,
        last: Int
    ): Result<List<WorkoutListItem>, WorkoutError> {
        val accessToken = tokenStorage.tokens.firstOrNull()?.accessToken
            ?: return Result.Error(
                WorkoutError.LocalError(WorkoutError.Local.TOKEN_NOT_FOUND)
            )

        return workoutRepository.getWorkoutHistory(
            accessToken = "Bearer $accessToken",
            from = from,
            to = to,
            last = last
        )
    }
}
