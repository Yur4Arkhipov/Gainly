package com.jacqulin.gainly.core.data.repository

import android.util.Log
import com.jacqulin.gainly.core.data.local.dao.WorkoutDao
import com.jacqulin.gainly.core.data.mappers.toCreateRequestDto
import com.jacqulin.gainly.core.data.mappers.toDomain
import com.jacqulin.gainly.core.data.remote.service.WorkoutApiService
import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.core.domain.model.workout.WorkoutById
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.AppError
import com.jacqulin.gainly.core.util.errors.ErrorHandler
import com.jacqulin.gainly.core.util.errors.WorkoutError
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import retrofit2.HttpException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutApiService: WorkoutApiService,
    private val workoutDao: WorkoutDao
) : WorkoutRepository {
    override suspend fun createWorkout(
        accessToken: String,
        workout: Workout
    ): Result<WorkoutId, WorkoutError> {
        return try {
            val request = workout.toCreateRequestDto()
            Log.d("WorkoutRepository", "Create workout request: $request")
            val response = workoutApiService.createWorkout(accessToken, request)
            Log.d("WorkoutRepository", "Create workout response: $response")
            Result.Success(response.toDomain())
        } catch (e: Throwable) {
            Log.d("WorkoutRepository", "Create workout error: $e")
            Result.Error(ErrorHandler.mapWorkoutError(e))
        }
    }

    override suspend fun getWorkoutHistory(
        accessToken: String,
        from: String,
        to: String,
        last: Int
    ): Result<List<Workout>, WorkoutError> {
        return try {
            val response = workoutApiService.getWorkoutHistory(
                accessToken = accessToken,
                from = from,
                to = to,
                last = last
            )
            Log.d("WorkoutRepository", "Get workout history response: $response")

            val workouts = coroutineScope {
                response.map { workoutListItemDto ->
                    async {
                        val workoutListItem = workoutListItemDto.toDomain()
                        Log.d("WorkoutRepository", "workoutListItem: $workoutListItem")
                        val workoutByIdDto = workoutApiService.getWorkoutById(
                            accessToken = accessToken,
                            workoutId = workoutListItem.workoutId
                        )
                        Log.d("WorkoutRepository", "workoutByIdDto: $workoutByIdDto")
                        val workoutById = workoutByIdDto.toDomain()
                        Log.d("WorkoutRepository", "workoutById: $workoutById")
                        Workout(
                            id = workoutListItem.workoutId,
                            date = workoutListItem.date,
                            title = workoutListItem.title,
                            exercises = workoutById.exercises
                        )
                    }
                }.awaitAll()
            }

            Result.Success(workouts)

        } catch (e: Exception) {
            if (e is CancellationException) throw e

            Log.d("WorkoutRepository", "Get workout history error: $e")
            Result.Error(ErrorHandler.mapWorkoutError(e))
        }
    }

    override suspend fun getFriendWorkoutHistory(
        accessToken: String,
        username: String,
        from: String,
        to: String,
        last: Int
    ): Result<List<Workout>, AppError> {
        return try {
            val response = workoutApiService.getFriendWorkoutHistory(
                accessToken = accessToken,
                friendsname = username,
                from = from,
                to = to,
                last = last
            )
            Log.d("WorkoutRepository", "Get friend workout history response: $response")
            val workouts = coroutineScope {
                response.map { workoutListItemDto ->
                    async {
                        val workoutListItem = workoutListItemDto.toDomain()
                        val workoutById = workoutApiService.getWorkoutById(
                            accessToken = accessToken,
                            workoutId = workoutListItem.workoutId
                        ).toDomain()
                        Workout(
                            date = workoutListItem.date,
                            title = workoutListItem.title,
                            exercises = workoutById.exercises
                        )
                    }
                }.awaitAll()
            }

            Result.Success(workouts)

        } catch (e: Throwable) {
            if (e is CancellationException) throw e

            Log.d("WorkoutRepository", "Get friend workout history error: $e")
            Result.Error(ErrorHandler.mapAppError(e))
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
            Log.d("WorkoutRepository", "Get workout by id response: $response")
            val result = response.toDomain()

            Result.Success(result)
        } catch (e: Throwable) {
            Log.d("WorkoutRepository", "Get workout by id error: $e")
            Result.Error(ErrorHandler.mapWorkoutError(e))
        }
    }

    override suspend fun deleteWorkout(
        accessToken: String,
        workoutId: String
    ): Result<Unit, AppError> {
        return try {
            val response = workoutApiService.deleteWorkoutById(
                accessToken = accessToken,
                workoutId = workoutId
            )

            val result = if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(ErrorHandler.mapAppError(HttpException(response)))
            }

            result
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.Error(ErrorHandler.mapAppError(e))
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
