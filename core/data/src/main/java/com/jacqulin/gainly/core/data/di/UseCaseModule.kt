package com.jacqulin.gainly.core.data.di

import com.jacqulin.gainly.core.data.usecase.SignInUseCaseImpl
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.SignInUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
//abstract class UseCaseModule {

    @Provides
    fun provideSignInUseCase(repository: AuthRepository) : SignInUseCase {
        return SignInUseCaseImpl(repository)
    }
//    @Binds
//    abstract fun provideSignInUseCase(
//        impl: SignInUseCaseImpl
//    ): SignInUseCase
}