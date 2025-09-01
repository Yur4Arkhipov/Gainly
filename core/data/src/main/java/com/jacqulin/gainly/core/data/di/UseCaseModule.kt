package com.jacqulin.gainly.core.data.di

import com.jacqulin.gainly.core.data.usecase.auth.ClearTokensUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.SendCodeToEmailUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.GetGoogleIdTokenUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.GetRefreshTokenUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.LogoutUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.SaveTokensUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.SignInGoogleUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.SignInTelegramUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.SignInUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.SignUpUseCaseImpl
import com.jacqulin.gainly.core.data.usecase.auth.VerifyCodeUseCaseImpl
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.usecase.auth.ClearTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SendCodeToEmailUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.GetGoogleIdTokenUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.GetRefreshTokenUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.LogoutUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SaveTokensUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignInGoogleUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignInTelegramUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignInUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.SignUpUseCase
import com.jacqulin.gainly.core.domain.usecase.auth.VerifyCodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSignInUseCase(repository: AuthRepository) : SignInUseCase {
        return SignInUseCaseImpl(repository)
    }

    @Provides
    fun provideSignUpUseCase(repository: AuthRepository) : SignUpUseCase {
        return SignUpUseCaseImpl(repository)
    }

    @Provides
    fun provideSaveTokensUseCase(tokenStorage: TokenStorage): SaveTokensUseCase {
        return SaveTokensUseCaseImpl(tokenStorage)
    }

    @Provides
    fun provideSendCodeToEmailUseCase(repository: AuthRepository) : SendCodeToEmailUseCase {
        return SendCodeToEmailUseCaseImpl(repository)
    }

    @Provides
    fun provideVerifyCodeUseCase(repository: AuthRepository) : VerifyCodeUseCase {
        return VerifyCodeUseCaseImpl(repository)
    }

    @Provides
    fun provideSignInGoogleUseCase(repository: AuthRepository) : SignInGoogleUseCase {
        return SignInGoogleUseCaseImpl(repository)
    }

    @Provides
    fun provideGetTokenUseCase(repository: AuthRepository) : GetGoogleIdTokenUseCase {
        return GetGoogleIdTokenUseCaseImpl(repository)
    }

    @Provides
    fun provideLogoutUseCase(
        repository: AuthRepository,
        getRefreshTokensUseCase: GetRefreshTokenUseCase
    ) : LogoutUseCase {
        return LogoutUseCaseImpl(repository, getRefreshTokensUseCase)
    }

    @Provides
    fun provideClearTokensUseCase(tokenStorage: TokenStorage) : ClearTokensUseCase {
        return ClearTokensUseCaseImpl(tokenStorage)
    }

    @Provides
    fun provideGetRefreshTokenUseCase(tokenStorage: TokenStorage) : GetRefreshTokenUseCase {
        return GetRefreshTokenUseCaseImpl(tokenStorage)
    }

    @Provides
    fun provideSignInTelegramUseCase(repository: AuthRepository) : SignInTelegramUseCase {
        return SignInTelegramUseCaseImpl(repository)
    }
}