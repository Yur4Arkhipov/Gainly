package com.jacqulin.gainly.feature.workout.presentation.model

import com.jacqulin.gainly.core.designsystem.R

sealed class WorkoutType(val label: String, val iconRes: Int) {
    object Cardio : WorkoutType("Кардио тренировка", R.drawable.ic_cardio)
    object Strength : WorkoutType("Силовая тренировка", R.drawable.ic_strength)

    companion object {
        val entries by lazy { listOf(Cardio, Strength) }
    }
}
