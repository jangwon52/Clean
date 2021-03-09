package com.mongoose.clean.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mongoose.clean.data.model.RemoteKeys
import com.mongoose.clean.data.model.user.UserEntity

/**
 *  UsersDb.kt
 *
 *  Created by jangwon on 2021/03/09
 *
 */

@Database(
    entities = [UserEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class UsersDb : RoomDatabase() {
    companion object {
        fun create(context: Context, useInMemory: Boolean): UsersDb {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, UsersDb::class.java)
            } else {
                Room.databaseBuilder(context, UsersDb::class.java, "users.db")
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun usersDao(): UsersDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}