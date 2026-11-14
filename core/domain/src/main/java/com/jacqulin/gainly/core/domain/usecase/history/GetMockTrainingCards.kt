//package com.jacqulin.gainly.feature.history.data.usecase


//fun getMockTrainingCards(
//    date: LocalDate
//): List<WorkoutCardUi> {
//    return when (date.dayOfWeek) {
//
//        DayOfWeek.MONDAY -> listOf(
//            WorkoutCardUi(
//                id = "1",
//                trainingInfoList = listOf(
//                    WorkoutInfoUi(
//                        title = "Подтягивания",
//                        text = "4 × 10"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Отжимания",
//                        text = "4 × 15"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Приседания",
//                        text = "smth"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Жим",
//                        text = "smth"
//                    )
//                )
//            ),
//            WorkoutCardUi(
//                id = "2",
//                trainingInfoList = listOf(
//                    WorkoutInfoUi(
//                        title = "Приседания",
//                        text = "3 × 20"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Подтягивания",
//                        text = "smth"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Отжимания",
//                        text = "smth"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Тяга",
//                        text = "smth"
//                    )
//                )
//            ),
//            WorkoutCardUi(
//                id = "3",
//                trainingInfoList = listOf(
//                    WorkoutInfoUi(
//                        title = "Приседания",
//                        text = "3 × 20"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Подтягивания",
//                        text = "smth"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Отжимания",
//                        text = "smth"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Тяга",
//                        text = "smth"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Тяга",
//                        text = "smth"
//                    )
//                )
//            ),
//            WorkoutCardUi(
//                id = "4",
//                trainingInfoList = listOf(
//                    WorkoutInfoUi(
//                        title = "Приседания",
//                        text = "3 × 20"
//                    )
//                )
//            ),
//            WorkoutCardUi(
//                id = "5",
//                trainingInfoList = listOf(
//                    WorkoutInfoUi(
//                        title = "Приседания",
//                        text = "3 × 20"
//                    )
//                )
//            )
//        )
//
//        DayOfWeek.TUESDAY -> listOf(
//            WorkoutCardUi(
//                id = "6",
//                trainingInfoList = listOf(
//                    WorkoutInfoUi(
//                        title = "Бег",
//                        text = "5 км"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Подтягивания",
//                        text = "smth"
//                    )
//                )
//            )
//        )
//        DayOfWeek.WEDNESDAY -> listOf(
//            WorkoutCardUi(
//                id = "7",
//                trainingInfoList = listOf(
//                    WorkoutInfoUi(
//                        title = "Жим лёжа",
//                        text = "4 × 8"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Тяга",
//                        text = "4 × 10"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Планка",
//                        text = "3 × 60 сек"
//                    ),
//                    WorkoutInfoUi(
//                        title = "Бег",
//                        text = "smth"
//                    )
//                )
//            )
//        )
//
//        else -> emptyList()
//    }
//}
//
//fun getMockWorkouts(): List<WorkoutCardUi> {
//    return listOf(
//        WorkoutCardUi(
//            title = "Подтягивания",
//            time = "fdsf"
//        ),
//        WorkoutCardUi(
//            title = "Подтягивания",
//            time = "fdsf"
//        ),
//        WorkoutCardUi(
//            title = "Подтягивания",
//            time = "fdsf"
//        ),
//        WorkoutCardUi(
//            title = "Подтягивания",
//            time = "fdsf"
//        ),
//        WorkoutCardUi(
//            title = "Подтягивания",
//            time = "fdsf"
//        )
//    )
//}