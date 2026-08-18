package com.jacqulin.gainly.core.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey
    val id: String,
    val date: String
)

@Entity(
    tableName = "exercises",
    foreignKeys = [
        ForeignKey(
            entity = WorkoutEntity::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val workoutId: String,
    val name: String
)

@Entity(
    tableName = "workout_sets",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutSetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exerciseId: Int,
    val reps: Int,
    val weight: Int,
    val isOwnWeight: Boolean
)

//data class WorkoutWithExercises(
//    @Embedded
//    val workout: WorkoutEntity,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "workoutId"
//    )
//    val exercises: List<ExerciseWithSets>
//)
//
//data class ExerciseWithSets(
//    @Embedded
//    val exercise: ExerciseEntity,
//    @Relation(
//        parentColumn = "id",
//        entityColumn = "exerciseId"
//    )
//    val sets: List<WorkoutSetEntity>
//)