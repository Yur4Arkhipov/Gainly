package com.jacqulin.gainly.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jacqulin.gainly.core.data.local.dao.FriendDao
import com.jacqulin.gainly.core.data.local.dao.WorkoutDao
import com.jacqulin.gainly.core.data.local.entity.ExerciseEntity
import com.jacqulin.gainly.core.data.local.entity.FriendEntity
import com.jacqulin.gainly.core.data.local.entity.WorkoutEntity
import com.jacqulin.gainly.core.data.local.entity.WorkoutSetEntity

@Database(
    entities = [
        FriendEntity::class,
        WorkoutEntity::class,
        ExerciseEntity::class,
        WorkoutSetEntity::class
    ],
    version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun friendDao(): FriendDao
    abstract fun workoutDao(): WorkoutDao

    companion object {
//        val MIGRATION_1_2 = object : Migration(1, 2) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                db.execSQL("ALTER TABLE meal ADD COLUMN imageUri TEXT")
//            }
//        }
//
//        val MIGRATION_2_3 = object : Migration(2, 3) {
//            override fun migrate(db: SupportSQLiteDatabase) {
//                db.execSQL("ALTER TABLE meal ADD COLUMN isFavorite INTEGER NOT NULL DEFAULT 0")
//            }
//        }
    }
}