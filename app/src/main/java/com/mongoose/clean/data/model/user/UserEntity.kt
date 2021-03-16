package com.mongoose.clean.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  UserEntity.kt
 *
 *  Created by jangwon on 2021/03/09
 *
 */

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey
    val id: String,
    val cell: String,
    val email: String,
    val name: String,
    val thumbnail: String,
) {
    companion object {
        fun mapFromSpec(data: Result) = UserEntity(
            id = data.cell + data.name.first + data.name.last,
            cell = data.cell,
            email = data.email,
            name = data.name.title + data.name.first + data.name.last,
            thumbnail = data.picture.thumbnail
        )
    }
}