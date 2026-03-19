package com.jacqulin.gainly.core.data.di

import android.content.Context
import androidx.room.Room
import com.jacqulin.gainly.core.data.local.dao.FriendDao
import com.jacqulin.gainly.core.data.local.database.FriendsDatabase
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
    fun provideDatabase(@ApplicationContext context: Context): FriendsDatabase =
        Room.databaseBuilder(
            context,
            FriendsDatabase::class.java,
            "friends"
        )
//            .addMigrations(FriendsDatabase.MIGRATION_1_2, FriendsDatabase.MIGRATION_2_3)
            .build()

    @Provides
    fun provideFriendDao(db: FriendsDatabase): FriendDao = db.friendDao()
}