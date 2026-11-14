package com.jacqulin.gainly.feature.history.di

import com.jacqulin.gainly.feature.history.data.repository.TimeProviderImpl
import com.jacqulin.gainly.feature.history.domain.repository.TimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HistoryModule {

    @Binds
    @Singleton
    abstract fun bindTimeProvider(
        timeProviderImpl: TimeProviderImpl
    ): TimeProvider
}
