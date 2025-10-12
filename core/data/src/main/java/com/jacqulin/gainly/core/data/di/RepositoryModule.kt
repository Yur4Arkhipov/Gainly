package com.jacqulin.gainly.core.data.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.jacqulin.gainly.core.data.remote.service.AuthApiService
import com.jacqulin.gainly.core.data.remote.service.FriendsApiService
import com.jacqulin.gainly.core.data.repository.AuthRepositoryImpl
import com.jacqulin.gainly.core.data.repository.FriendsRepositoryImpl
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import com.jacqulin.gainly.core.domain.repository.FriendsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager {
        return CredentialManager.create(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApiService,
        credentialManager: CredentialManager,
    ): AuthRepository {
        return AuthRepositoryImpl(api, credentialManager)
    }

    @Provides
    @Singleton
    fun provideFriendsRepository(
        api: FriendsApiService,
    ): FriendsRepository {
        return FriendsRepositoryImpl(api)
    }
}