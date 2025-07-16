package com.jacqulin.gainly.core.data.di

import com.jacqulin.gainly.core.data.usecase.auth.SaveTokensUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.SignInUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.SignUpUseCaseImpl
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.SaveTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignInUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignUpUseCase
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

    @Provides
    fun provideSignUpUseCase(repository: AuthRepository) : SignUpUseCase {
        return SignUpUseCaseImpl(repository)
    }

    @Provides
    fun provideSaveTokensUseCase(tokenStorage: TokenStorage): SaveTokensUseCase {
        return SaveTokensUseCaseImpl(tokenStorage)
    }
}