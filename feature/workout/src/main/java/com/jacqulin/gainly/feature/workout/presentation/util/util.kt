package com.jacqulin.gainly.feature.workout.presentation.util

fun formatDate(digits: String): String {
    return buildString {
        digits.forEachIndexed { index, c ->
            append(c)
            if ((index == 1 || index == 3) && index != digits.lastIndex) {
                append('.')
            }
        }
    }
}
