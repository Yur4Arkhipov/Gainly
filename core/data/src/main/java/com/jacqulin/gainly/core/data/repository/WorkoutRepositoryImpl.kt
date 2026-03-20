package com.jacqulin.gainly.core.data.repository

import android.util.Log
import com.jacqulin.gainly.core.data.local.dao.WorkoutDao
import com.jacqulin.gainly.core.data.remote.dto.workout.ExerciseDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutRequestDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutSetDto
import com.jacqulin.gainly.core.data.remote.service.WorkoutApiService
import com.jacqulin.gainly.core.domain.model.workout.ExerciseData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutById
import com.jacqulin.gainly.core.domain.model.workout.WorkoutData
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.domain.model.workout.WorkoutSetData
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.ErrorHandler
import com.jacqulin.gainly.core.util.errors.WorkoutError
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutApiService: WorkoutApiService,
    private val workoutDao: WorkoutDao
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
//
//            // Синхронизация с локальной БД
//            syncWorkoutsToDatabase(response)

            Result.Success(items)
        } catch (e: Throwable) {
            Result.Error(ErrorHandler.mapWorkoutError(e))
        }
    }

    override suspend fun getWorkoutById(
        accessToken: String,
        workoutId: String
    ): Result<WorkoutById, WorkoutError> {
        return try {
            val response = workoutApiService.getWorkoutById(
                accessToken = accessToken,
                workoutId = workoutId
            )
            val result = WorkoutById(
                id = response.id,
                date = response.date,
                exercises = response.exercises.map { exercise ->
                    ExerciseData(
                        name = exercise.name,
                        sets = exercise.sets.map { set ->
                            WorkoutSetData(
                                reps = set.reps,
                                weight = set.weight,
                                isOwnWeight = set.isOwnWeight
                            )
                        }
                    )
                }
            )
//            // Сохраняем в локальную БД
//            syncSingleWorkoutToDatabase(response)
//
//            val result = response.toWorkoutById()
            Result.Success(result)

        } catch (e: Throwable) {
            Result.Error(ErrorHandler.mapWorkoutError(e))
        }
    }
//
//    private suspend fun syncWorkoutsToDatabase(workouts: List<com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutItemDto>) {
//        workouts.forEach { dto ->
//            val workoutEntity = com.jacqulin.gainly.core.data.local.entity.WorkoutEntity(
//                id = dto.id,
//                date = dto.date
//            )
//            workoutDao.insertWorkout(workoutEntity)
//
//            // Сохраняем упражнения если они есть
//            dto.exercises?.let { exercises ->
//                val exerciseDtos = exercises.filterIsInstance<ExerciseDto>()
//                val exerciseEntities = exerciseDtos.mapIndexed { index, exercise ->
//                    exercise.toEntity(dto.id, index)
//                }
//                workoutDao.insertExercises(exerciseEntities)
//
//                // Сохраняем подходы
//                exerciseEntities.forEachIndexed { index, exerciseEntity ->
//                    val setEntities = exerciseDtos.getOrNull(index)?.sets?.map {
//                        it.toEntity(exerciseEntity.id)
//                    } ?: emptyList()
//                    workoutDao.insertSets(setEntities)
//                }
//            }
//        }
//    }

//    private suspend fun syncSingleWorkoutToDatabase(response: com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutResponseDto) {
//        val workoutEntity = response.toEntity()
//        workoutDao.insertWorkout(workoutEntity)
//
//        val exerciseEntities = response.exercises.mapIndexed { index, exercise ->
//            exercise.toEntity(response.id, index)
//        }
//        workoutDao.insertExercises(exerciseEntities)
//
//        exerciseEntities.forEachIndexed { index, exerciseEntity ->
//            val setEntities = response.exercises.getOrNull(index)?.sets?.map {
//                it.toEntity(exerciseEntity.id)
//            } ?: emptyList()
//            workoutDao.insertSets(setEntities)
//        }
//    }
}