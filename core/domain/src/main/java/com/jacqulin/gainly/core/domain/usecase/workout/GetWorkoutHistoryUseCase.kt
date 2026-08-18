package com.jacqulin.gainly.core.domain.usecase.workout

import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError

interface GetWorkoutHistoryUseCase {
    suspend operator fun invoke(
        from: String,
        to: String,
        last: Int
    ): Result<List<WorkoutListItem>, WorkoutError>
}

