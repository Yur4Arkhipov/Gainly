package com.jacqulin.gainly.core.data.di

import com.jacqulin.gainly.core.data.auth.TokenDataStore
import com.jacqulin.gainly.core.data.auth.TokenStorageImpl
import com.jacqulin.gainly.core.domain.auth.TokenStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object TokenStorageModule {

    @Provides
    fun provideTokenStorage(tokenDataStore: TokenDataStore) : TokenStorage {
        return TokenStorageImpl(tokenDataStore)
    }
}
