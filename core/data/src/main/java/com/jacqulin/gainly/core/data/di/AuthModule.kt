package com.jacqulin.gainly.core.data.di

import com.jacqulin.gainly.core.data.auth.AuthManager
import com.jacqulin.gainly.core.data.auth.JwtTokenValidator
import com.jacqulin.gainly.core.data.auth.TokenDataStore
import com.jacqulin.gainly.core.data.auth.TokenRefresherImpl
import com.jacqulin.gainly.core.data.auth.TokenStorageImpl
import com.jacqulin.gainly.core.data.remote.service.AuthApiService
import com.jacqulin.gainly.core.domain.auth.TokenRefresher
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import com.jacqulin.gainly.core.domain.auth.TokenValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    fun provideTokenStorage(tokenDataStore: TokenDataStore) : TokenStorage {
        return TokenStorageImpl(tokenDataStore)
    }

    @Provides
    fun provideTokenValidator(): TokenValidator = JwtTokenValidator()

    @Provides
    fun provideTokenRefresher(api: AuthApiService): TokenRefresher =
        TokenRefresherImpl(api)

    @Provides
    @Singleton
    fun provideAuthManager(
        tokenStorage: TokenStorage,
        tokenValidator: TokenValidator,
        tokenRefresher: TokenRefresher
    ): AuthManager {
        return AuthManager(tokenStorage, tokenValidator, tokenRefresher)
    }
}