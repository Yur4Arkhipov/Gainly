package com.jacqulin.gainly.core.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jacqulin.gainly.core.data.local.entity.FriendEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FriendDao {
    @Query("SELECT * FROM friends")
    fun observeFriends(): Flow<List<FriendEntity>>

    @Query("""
        SELECT * FROM friends 
        WHERE LOWER(username) LIKE '%' || LOWER(:query) || '%'
    """)
    fun searchFriends(query: String): Flow<List<FriendEntity>>

    @Query("SELECT * FROM friends")
    suspend fun getAllFriendsOnce(): List<FriendEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(friends: List<FriendEntity>)

    @Query("DELETE FROM friends WHERE userId IN (:ids)")
    suspend fun deleteFriendsByIds(ids: List<String>)
}

