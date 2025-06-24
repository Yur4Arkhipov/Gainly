package com.example.gainly.di.datamodule

import com.example.gainly.data.remote.service.AuthApiService
import com.example.gainly.data.repository.AuthRepositoryImpl
import com.example.gainly.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fitness-app-auth-api.fly.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    fun provideAuthRepository(api: AuthApiService): AuthRepository {
        return AuthRepositoryImpl(api)
    }
}