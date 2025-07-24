package com.jacqulin.gainly.core.data.di

import com.jacqulin.gainly.core.data.remote.service.AuthApiService
import com.jacqulin.gainly.core.data.repository.AuthRepositoryImpl
import com.jacqulin.gainly.core.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideAuthRepository(api: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(api)
    }
}