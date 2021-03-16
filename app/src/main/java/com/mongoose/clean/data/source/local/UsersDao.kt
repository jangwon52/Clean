package com.mongoose.clean.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mongoose.clean.data.model.user.UserEntity

/**
 *  UsersDao.kt
 *
 *  Created by jangwon on 2021/03/09
 *
 */

@Dao
interface UsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(posts: List<UserEntity>)

    @Query("SELECT * FROM user")
    fun selectAll(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM user")
    fun deleteAll()
}
