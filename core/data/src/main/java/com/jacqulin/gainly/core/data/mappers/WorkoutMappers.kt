package com.jacqulin.gainly.core.data.mappers

import com.jacqulin.gainly.core.data.remote.dto.workout.CreateWorkoutRequestDto
import com.jacqulin.gainly.core.data.remote.dto.workout.ExerciseDto
import com.jacqulin.gainly.core.data.remote.dto.workout.ExerciseListItemDto
import com.jacqulin.gainly.core.data.remote.dto.workout.SetDto
import com.jacqulin.gainly.core.data.remote.dto.workout.SetListItemDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutByIdDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutIdDto
import com.jacqulin.gainly.core.data.remote.dto.workout.WorkoutListItemDto
import com.jacqulin.gainly.core.domain.model.workout.Exercise
import com.jacqulin.gainly.core.domain.model.workout.ExerciseListItem
import com.jacqulin.gainly.core.domain.model.workout.SetListItem
import com.jacqulin.gainly.core.domain.model.workout.WorkoutId
import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.domain.model.workout.Set
import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.core.domain.model.workout.WorkoutById

fun WorkoutIdDto.toDomain(): WorkoutId = WorkoutId(workoutId = workoutId)

fun WorkoutId.toDto(): WorkoutIdDto = WorkoutIdDto(workoutId = workoutId)

fun WorkoutListItemDto.toDomain(): WorkoutListItem = WorkoutListItem(
    workoutId = this.workoutId,
    userId = this.userId,
    title = this.title,
    date = this.date,
    exercises = emptyList()
)

fun WorkoutListItem.toDto(): WorkoutListItemDto = WorkoutListItemDto(
    workoutId = this.workoutId,
    userId = this.userId,
    title = this.title,
    date = this.date,
    exercises = this.exercises.map { it.toDto() }
)

fun ExerciseListItemDto.toDomain(): ExerciseListItem = ExerciseListItem(
    exerciseId = this.exerciseId,
    workoutId = this.workoutId,
    title = this.title,
    sets = this.sets.toDomain()
)

fun ExerciseListItem.toDto(): ExerciseListItemDto = ExerciseListItemDto(
    exerciseId = this.exerciseId,
    workoutId = this.workoutId,
    title = this.title,
    sets = this.sets.toDto()
)

fun SetListItemDto.toDomain(): SetListItem = SetListItem(
    setId = this.setId,
    exerciseId = this.exerciseId,
    reps = this.reps,
    weight = this.weight,
    isOwnWeight = this.isOwnWeight,
    title = this.title
)

fun SetListItem.toDto(): SetListItemDto = SetListItemDto(
    setId = this.setId,
    exerciseId = this.exerciseId,
    reps = this.reps,
    weight = this.weight,
    isOwnWeight = this.isOwnWeight,
    title = this.title
)

fun WorkoutByIdDto.toDomain(): WorkoutById = WorkoutById(
    workoutId = this.workoutId,
    date = this.date,
    exercises = this.exercises.map { it.toDomain() }
)

fun WorkoutById.toDto(): WorkoutByIdDto = WorkoutByIdDto(
    date = this.date,
    workoutId = this.workoutId,
    exercises = this.exercises.map { it.toDto() }
)

fun ExerciseDto.toDomain(): Exercise = Exercise(
    title = this.title,
    sets = this.sets.map { it.toDomain() }
)

fun Exercise.toDto(): ExerciseDto = ExerciseDto(
    title = this.title,
    sets = this.sets.map { it.toDto() }
)

fun SetDto.toDomain(): Set = Set(
    reps = this.reps,
    weight = this.weight,
    isOwnWeight = this.isOwnWeight
)

fun Set.toDto(): SetDto = SetDto(
    reps = this.reps,
    weight = this.weight,
    isOwnWeight = this.isOwnWeight
)

fun Workout.toCreateRequestDto(): CreateWorkoutRequestDto = CreateWorkoutRequestDto(
    date = this.date,
    title = this.title,
    exercises = this.exercises.map { it.toDto() }
)
