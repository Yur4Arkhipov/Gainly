package com.jacqulin.gainly.core.data.di

import com.jacqulin.gainly.core.data.remote.service.AuthApiService
import com.jacqulin.gainly.core.data.remote.service.FriendsApiService
import com.jacqulin.gainly.core.data.remote.service.HealthApiService
import com.jacqulin.gainly.core.data.remote.service.WorkoutApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor { message ->
            android.util.Log.d("Workout", message)
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain
                    .request()
                    .newBuilder()
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://gainly.site/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAuthApiService(retrofit: Retrofit): AuthApiService {
        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    fun provideHealthApiService(retrofit: Retrofit): HealthApiService {
        return retrofit.create(HealthApiService::class.java)
    }

    @Provides
    fun provideFriendsApiService(retrofit: Retrofit): FriendsApiService {
        return retrofit.create(FriendsApiService::class.java)
    }

    @Provides
    fun provideWorkoutApiService(retrofit: Retrofit): WorkoutApiService {
        return retrofit.create(WorkoutApiService::class.java)
    }
}