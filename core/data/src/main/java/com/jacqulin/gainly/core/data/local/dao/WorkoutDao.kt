package com.jacqulin.gainly.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jacqulin.gainly.core.data.local.entity.ExerciseEntity
import com.jacqulin.gainly.core.data.local.entity.WorkoutEntity
import com.jacqulin.gainly.core.data.local.entity.WorkoutSetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

//    @Transaction
//    @Query("SELECT * FROM workouts")
//    fun observeWorkouts(): Flow<List<WorkoutWithExercises>>

//    @Transaction
//    @Query("SELECT * FROM workouts WHERE id = :workoutId")
//    suspend fun getWorkoutById(workoutId: String): WorkoutWithExercises?

//    @Transaction
//    @Query("SELECT * FROM workouts ORDER BY date DESC LIMIT :limit")
//    suspend fun getRecentWorkouts(limit: Int): List<WorkoutWithExercises>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: WorkoutEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSets(sets: List<WorkoutSetEntity>)

//    @Query("DELETE FROM workouts WHERE id = :workoutId")
//    suspend fun deleteWorkout(workoutId: String)
//
//    @Query("DELETE FROM exercises WHERE workoutId = :workoutId")
//    suspend fun deleteExercises(workoutId: String)
//
//    @Query("DELETE FROM workout_sets WHERE exerciseId IN (SELECT id FROM exercises WHERE workoutId = :workoutId)")
//    suspend fun deleteSets(workoutId: String)

//    @Transaction
//    suspend fun deleteWorkoutCascade(workoutId: String) {
//        deleteSets(workoutId)
//        deleteExercises(workoutId)
//        deleteWorkout(workoutId)
//    }

//    @Query("DELETE FROM workouts")
//    suspend fun clearAllWorkouts()
//
//    @Query("DELETE FROM exercises")
//    suspend fun clearAllExercises()
//
//    @Query("DELETE FROM workout_sets")
//    suspend fun clearAllSets()

//    @Suppress("UNUSED")
//    @Transaction
//    suspend fun clearAll() {
//        clearAllSets()
//        clearAllExercises()
//        clearAllWorkouts()
//    }
}

