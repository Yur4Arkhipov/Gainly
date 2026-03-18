package com.jacqulin.gainly.core.data.repository

import android.util.Log
import com.jacqulin.gainly.core.data.remote.dto.workout.ExerciseDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutRequestDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutSetDto
import com.jacqulin.gainly.core.data.remote.service.WorkoutApiService
import com.jacqulin.gainly.core.domain.model.workout.WorkoutData
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import javax.inject.Inject

class WorkoutRepositoryImpl @Inject constructor(
    private val workoutApiService: WorkoutApiService
) : WorkoutRepository {
    override suspend fun createWorkout(workout: WorkoutData) {
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
//        val response = workoutApiService.createWorkout(request)
//        if (!response.isSuccessful) {
//            throw Exception("Failed to create workout: ${response.code()}")
//        }
    }
}