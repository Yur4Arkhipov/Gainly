package com.jacqulin.gainly.feature.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme

@Composable
fun OnboardingNextButton(
    currentPage: Int,
    totalPages: Int,
    onNext: () -> Unit,
    isLastPage: Boolean
) {
    val progress = currentPage / (totalPages - 1).toFloat()

    Box(
        modifier = Modifier
            .size(72.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 4.dp,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )

        IconButton(
            onClick = onNext,
            modifier = Modifier
                .size(56.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = if (isLastPage) Icons.Default.Check else Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = if (isLastPage) "Finish" else "Next",
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingNextButtonPreview() {
    GainlyTheme {
        OnboardingNextButton(
            currentPage = 1,
            totalPages = 3,
            onNext = {},
            isLastPage = false
        )
    }
}

