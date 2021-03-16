package com.mongoose.clean.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mongoose.clean.data.model.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    fun remoteKeysId(id: String): RemoteKeys?

    @Query("DELETE FROM remote_keys")
    fun clearRemoteKeys()
}