package com.jacqulin.gainly.core.domain.usecase.history

import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.model.workout.Exercise
import com.jacqulin.gainly.core.domain.model.workout.Set
import com.jacqulin.gainly.core.domain.model.workout.Workout
import com.jacqulin.gainly.core.domain.repository.WorkoutRepository
import com.jacqulin.gainly.core.util.Result
import com.jacqulin.gainly.core.util.errors.WorkoutError
import kotlinx.coroutines.flow.firstOrNull
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

class GetWorkoutsForDateUseCase @Inject constructor(
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
            to = date.plusDays(1).toString(),
            last = 20
        )
    }
}

/*class GetWorkoutsForDateUseCase @Inject constructor(

) {
    suspend operator fun invoke(date: LocalDate): Result<List<Workout>, WorkoutError> {
        return getWorkouts(date)
    }
}*/

fun getWorkouts(date: LocalDate): Result<List<Workout>, WorkoutError> {
    val res = when (date.dayOfWeek) {
        DayOfWeek.MONDAY -> listOf(
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
        )
        DayOfWeek.TUESDAY -> listOf(
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
            ),
        )
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY -> listOf(
            createWorkout(
                title = "Кардио",
                date = date,
                exercises = listOf(
                    createExercise(
                        "Бег",
                        listOf(
                            createSet(30, 1)
                        )
                    ),
                    createExercise(
                        "Прыжки на скакалке",
                        listOf(
                            createSet(100, 1),
                            createSet(100, 2),
                            createSet(100, 3)
                        )
                    )
                )
            ),
            createWorkout(
                title = "Тренировка ног",
                date = date,
                exercises = listOf(
                    createExercise(
                        "Приседания",
                        listOf(
                            createSet(15, 40, false),
                            createSet(12, 50, false),
                            createSet(10, 60, false)
                        )
                    ),
                    createExercise(
                        "Выпады",
                        listOf(
                            createSet(12, 10, false),
                            createSet(12, 10, false),
                            createSet(10, 15, false)
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
                            createSet(25,  2),
                            createSet(20,  3)
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
                )
            ),
        )
        DayOfWeek.SATURDAY,
        DayOfWeek.SUNDAY,
        DayOfWeek.WEDNESDAY -> listOf(
            createWorkout(
                title = "Вечерняя тренировка",
                date = date,
                exercises = listOf(
                    createExercise(
                        "Планка",
                        listOf(
                            createSet(60, 1),
                            createSet(45, 2),
                            createSet(45, 3)
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
