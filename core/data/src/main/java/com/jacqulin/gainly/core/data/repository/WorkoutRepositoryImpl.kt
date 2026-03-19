package com.jacqulin.gainly.core.data.repository

import android.util.Log
import com.jacqulin.gainly.core.data.remote.dto.workout.ExerciseDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutRequestDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutSetDto
import com.jacqulin.gainly.core.data.remote.service.WorkoutApiService
import com.jacqulin.gainly.core.domain.model.workout.WorkoutData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.ErrorHandler
import com.jacqulin.gainly.core.util.errors.WorkoutError
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutApiService: WorkoutApiService
) : WorkoutRepository {
    override suspend fun createWorkout(accessToken: String, workout: WorkoutData): Result<WorkoutId, WorkoutError> {
        return try {
            val request = WorkoutRequestDto(
                title = workout.title,
                exercises = workout.exercises.map { exercise ->
                    ExerciseDto(
                        name = exercise.name,
                        sets = exercise.sets.map { set ->
                            WorkoutSetDto(
                                reps = set.reps,
                                weight = set.weight,
                                isOwnWeight = set.isOwnWeight
                            )
                        }
                    )
                }
            )
            Log.d("Workout", "Workout: $request")
            val response = workoutApiService.createWorkout(accessToken, request)
            Log.d("Workout", "Response: $response")
            Result.Success(response)
        } catch (e: Throwable) {
            Result.Error(ErrorHandler.mapWorkoutError(e))
        }
    }

    override suspend fun getWorkoutHistory(
        accessToken: String,
        from: String,
        to: String,
        last: Int
    ): Result<List<WorkoutListItem>, WorkoutError> {
        return try {
            val response = workoutApiService.getWorkoutHistory(
                accessToken = accessToken,
                from = from,
                to = to,
                last = last
            )
            // response это уже List<WorkoutItemDto>, конвертируем в domain модель
            val items = response.map { dto ->
                WorkoutListItem(
                    workoutId = dto.id,
                    userId = dto.userId,
                    title = dto.title,
                    date = dto.date,
                    exerciseCount = dto.exercises?.size ?: 0,
                    duration = null
                )
            }
            Result.Success(items)
        } catch (e: Throwable) {
            Result.Error(ErrorHandler.mapWorkoutError(e))
        }
    }
}