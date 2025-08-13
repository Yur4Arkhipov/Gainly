package com.jacqulin.gainly.feature.onboarding

import androidx.annotation.DrawableRes

sealed class OnboardingModel(
    @DrawableRes val image: Int,
    val title: String,
    val description: String
) {

    data object FirstPage : OnboardingModel(
        image = R.drawable.onboarding1,
        title = "Ставь цели",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquet viverra duis bibendum nunc pellentesque."
    )

    data object SecondPage : OnboardingModel(
        image = R.drawable.onboarding2,
        title = "Заряжайся силой",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquet viverra duis bibendum nunc pellentesque."
    )

    data object ThirdPage : OnboardingModel(
        image = R.drawable.onboarding1,
        title = "Правильно питайся",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquet viverra duis bibendum nunc pellentesque."
    )

    data object FourthPage : OnboardingModel(
        image = R.drawable.onboarding2,
        title = "Улучшай качество сна",
        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquet viverra duis bibendum nunc pellentesque."
    )
}