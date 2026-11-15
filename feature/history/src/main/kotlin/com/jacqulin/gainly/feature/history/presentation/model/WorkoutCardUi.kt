package com.jacqulin.gainly.feature.history.presentation.model

data class WorkoutCardUi(
    val id: String = "0",
    val trainingInfoList: List<WorkoutInfoUi> = emptyList()
)
