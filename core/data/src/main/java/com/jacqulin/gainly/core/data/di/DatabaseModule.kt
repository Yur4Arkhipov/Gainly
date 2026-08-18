package com.jacqulin.gainly.core.data.di

import android.content.Context
import androidx.room.Room
import com.jacqulin.gainly.core.data.local.dao.FriendDao
import com.jacqulin.gainly.core.data.local.dao.WorkoutDao
import com.jacqulin.gainly.core.data.local.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        val db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "db"
        )
            .fallbackToDestructiveMigration(true)
//            .addMigrations(FriendsDatabase.MIGRATION_1_2, FriendsDatabase.MIGRATION_2_3)
            .build()
        return db
    }

    @Provides
    fun provideFriendDao(db: AppDatabase): FriendDao = db.friendDao()

    @Provides
    fun provideWorkoutDao(db: AppDatabase): WorkoutDao = db.workoutDao()
}