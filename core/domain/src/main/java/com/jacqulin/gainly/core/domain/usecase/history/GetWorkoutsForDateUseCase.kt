package com.jacqulin.gainly.core.domain.usecase.history

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.workout.Exercise
import com.jacqulin.gainly.core.domain.model.workout.ExerciseListItem
import com.jacqulin.gainly.core.domain.model.workout.Set
import com.jacqulin.gainly.core.domain.model.workout.SetListItem
import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.core.domain.model.workout.WorkoutListItem
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError
import kotlinx.coroutines.flow.firstOrNull
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.util.UUID
import javax.inject.Inject

/*class GetWorkoutsForDateUseCase @Inject constructor(
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
//            from = LocalDate.of(2024, 1, 1).toString(),
            to = date.plusDays(1).toString(),
//            to = LocalDate.now().plusDays(1).toString(),
            last = 20
        )
    }
}*/


class GetWorkoutsForDateUseCase @Inject constructor(

) {
    suspend operator fun invoke(date: LocalDate): Result<List<Workout>, WorkoutError> {
        return getWorkouts(date)
    }
}

fun getWorkouts(date: LocalDate): Result<List<Workout>, WorkoutError> {
    val res = when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> listOf(
/*            createWorkout(
                id = "workout-1",
                date = date,
                title = "Силовая тренировка",
                exercises = listOf(
                    createExercise(
                        workoutId = "workout-1",
                        exerciseId = "exercise-1",
                        title = "Подтягивания",
                        sets =
//                            listOf(
                            createSet(12, index = 1),
//                            createSet(10, index = 2),
//                            createSet(8, index = 3)
//                        )
                    ),
                    createExercise(
                        workoutId = "workout-1",
                        exerciseId = "exercise-2",
                        title = "Отжимания",
                        sets =
//                            listOf(
//                            createSet(20, index = 1),
                            createSet(25, index = 2),
//                            createSet(20, index = 3)
//                        )
                    ),
                    createExercise(
                        workoutId = "workout-1",
                        exerciseId = "exercise-3",
                        title = "Приседания",
                        sets =
//                            listOf(
                            createSet(15, 20, false, 1),
//                            createSet(15, 30, false, 2),
//                            createSet(12, 40, false, 3)
//                        )
                    ),
                    createExercise(
                        workoutId = "workout-1",
                        exerciseId = "exercise-4",
                        title = "Жим лёжа",
                        sets =
//                            listOf(
                            createSet(12, 40, false, 1),
//                            createSet(10, 50, false, 2),
//                            createSet(8, 60, false, 3),
//                            createSet(6, 70, false, 4)
//                        )
                    )
                )
            ),
            createWorkout(
                id = "workout-2",
                date = date,
                title = "Вечерняя тренировка",
                hour = 20,
                exercises = listOf(
                    createExercise(
                        "workout-2",
                        "exercise-5",
                        "Планка",
//                        listOf(
                            createSet(60, index = 1),
//                            createSet(45, index = 2),
//                            createSet(45, index = 3)
//                        )
                    ),
                    createExercise(
                        "workout-2",
                        "exercise-6",
                        "Бег",
//                        listOf(
                            createSet(20, index = 1)
//                        )
                    )
                )
            )*/
        )

        DayOfWeek.TUESDAY -> listOf(
/*            createWorkout(
                id = "workout-3",
                date = date,
                title = "Кардио",
                exercises = listOf(
                    createExercise(
                        "workout-3",
                        "exercise-7",
                        "Бег",
//                        listOf(
                            createSet(30, index = 1)
//                    )
                    ),
                    createExercise(
                        "workout-3",
                        "exercise-8",
                        "Прыжки на скакалке",
//                        listOf(
                            createSet(100, index = 1),
//                            createSet(100, index = 2),
//                            createSet(100, index = 3)
//                        )
                    )
                )
            )*/
        )

        DayOfWeek.WEDNESDAY -> listOf(
            createWorkout(
                date = date,
                title = "Тренировка верхней части тела",
                exercises = listOf(
                    createExercise(
                        "Жим лёжа",
                        listOf(
                            createSet(12, 40, false),
                            createSet(10, 50, false),
                            createSet(8, 60, false)
                        )
                    ),
                    createExercise(
                        "Тяга штанги",
                        listOf(
                            createSet(12, 30, false),
                            createSet(10, 40, false),
                            createSet(10, 40, false)
                        )
                    ),
                    createExercise(
                        "Подтягивания",
                        listOf(
                            createSet(10, 1),
                            createSet(8, 2),
                            createSet(6, 3)
                        )
                    )
                )
            ),
            createWorkout(
                title = "Силовая тренировка",
                date = date,
                exercises = listOf(
                    createExercise(
                        title = "Подтягивания",
                        sets = listOf(
                            createSet(12, 1),
                            createSet(10, 2),
                            createSet(8, 3)
                        )
                    ),
                    createExercise(
                        title = "Отжимания",
                        sets = listOf(
                            createSet(20, 1),
                            createSet(25, 2),
                            createSet(20, 3)
                        )
                    ),
                    createExercise(
                        title = "Приседания",
                        sets = listOf(
                            createSet(15, 20, false),
                            createSet(15, 30, false),
                            createSet(12, 40, false)
                        )
                    ),
                    createExercise(
                        title = "Жим лёжа",
                        sets = listOf(
                            createSet(12, 40, false),
                            createSet(10, 50, false),
                            createSet(8, 60, false),
                            createSet(6, 70, false)
                        )
                    )
                ),
            ),
            createWorkout(
                title = "Вечерняя тренировка",
                date = date,
                exercises = listOf(
                    createExercise(
                        "Планка",
                        listOf(
                            createSet(60, 1),
                            createSet(45, 2),
                            createSet(45,  3)
                        )
                    ),
                    createExercise(
                        "Бег",
                        listOf(
                            createSet(20, 1)
                        )
                    )
                )
            )
        )

        DayOfWeek.THURSDAY -> emptyList()

        DayOfWeek.FRIDAY -> listOf(
 /*           createWorkout(
                id = "workout-5",
                date = date,
                title = "Тренировка ног",
                exercises = listOf(
                    createExercise(
                        "workout-5",
                        "exercise-12",
                        "Приседания",
//                        listOf(
                            createSet(15, 40, false, 1),
//                            createSet(12, 50, false, 2),
//                            createSet(10, 60, false, 3)
//                        )
                    ),
                    createExercise(
                        "workout-5",
                        "exercise-13",
                        "Выпады",
//                        listOf(
                            createSet(12, 10, false, 1),
//                            createSet(12, 10, false, 2),
//                            createSet(10, 15, false, 3)
//                        )
                    )
                )
            )*/
        )

        DayOfWeek.SATURDAY -> listOf(
/*            createWorkout(
                id = "workout-6",
                date = date,
                title = "Функциональная тренировка",
                exercises = listOf(
                    createExercise(
                        "workout-6",
                        "exercise-14",
                        "Отжимания",
//                        listOf(
                            createSet(25, index = 1),
//                            createSet(20, index = 2),
//                            createSet(15, index = 3)
//                        )
                    ),
                    createExercise(
                        "workout-6",
                        "exercise-15",
                        "Подтягивания",
                            createSet(10, index = 1),
//                            createSet(8, index = 2),
//                            createSet(6, index = 3)
                    )
                )
            )*/
        )

        DayOfWeek.SUNDAY -> emptyList()
    }

    return Result.Success(res)
}

private fun createWorkout(
    date: LocalDate,
    title: String,
    exercises: List<Exercise>,
) = Workout(
    title = title,
    date = date.atTime(18, 0)
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toString(),
    exercises = exercises
)

private fun createExercise(
    title: String,
    sets: List<Set>
) = Exercise(
    title = title,
    sets = sets
)

private fun createSet(
    reps: Int,
    weight: Int = 0,
    isOwnWeight: Boolean = true,
) = Set(
    reps = reps,
    weight = weight,
    isOwnWeight = isOwnWeight,
)
