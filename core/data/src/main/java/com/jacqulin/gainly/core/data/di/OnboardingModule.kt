package com.jacqulin.gainly.core.data.di

import android.content.Context
import com.jacqulin.gainly.core.data.onboarding.OnboardingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnboardingModule {

    @Provides
    @Singleton
    fun provideOnboardingManager(
        @ApplicationContext context: Context
    ):  OnboardingManager {
        return OnboardingManager(context)
    }
}