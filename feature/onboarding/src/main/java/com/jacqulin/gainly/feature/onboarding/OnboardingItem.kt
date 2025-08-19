package com.jacqulin.gainly.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jacqulin.gainly.core.designsystem.theme.GainlyTheme
import com.jacqulin.gainly.core.designsystem.theme.type.LocalCustomOnboardingTypography

@Composable
fun OnboardingItem(onboardingModel: OnboardingModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = onboardingModel.image),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = onboardingModel.title,
            style = LocalCustomOnboardingTypography.current.onboardingTitle,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = onboardingModel.description,
            style = LocalCustomOnboardingTypography.current.onboardingDescription,
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        )
    }
}

@Preview(
    name = "First onboarding screen",
    showBackground = true
)
@Composable
fun OnboardingFirstItemPreview() {
    GainlyTheme {
        OnboardingItem(
            onboardingModel = OnboardingModel.FirstPage
        )
    }
}
@Preview(
    name = "Second onboarding screen",
    showBackground = true
)
@Composable
fun OnboardingSecondItemPreview() {
    GainlyTheme {
        OnboardingItem(
            onboardingModel = OnboardingModel.SecondPage
        )
    }
}