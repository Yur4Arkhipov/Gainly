package com.jacqulin.gainly.feature.onboarding

import androidx.annotation.DrawableRes
import com.jacqulin.gainly.core.designsystem.R

sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) {

    data object FirstPage : OnboardingModel(
        image = R.drawable.onboarding_back,
        title = "Ставь цели",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquet viverra duis bibendum nunc pellentesque."
    )

    data object SecondPage : OnboardingModel(
        image = R.drawable.onboarding_back,
        title = "Заряжайся силой",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquet viverra duis bibendum nunc pellentesque."
    )

    data object ThirdPage : OnboardingModel(
        image = R.drawable.onboarding_back,
        title = "Правильно питайся",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquet viverra duis bibendum nunc pellentesque."
    )

    data object FourthPage : OnboardingModel(
        image = R.drawable.onboarding_back,
        title = "Улучшай качество сна",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquet viverra duis bibendum nunc pellentesque."
    )
}