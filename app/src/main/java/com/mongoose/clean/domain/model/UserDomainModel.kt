package com.mongoose.clean.domain.model

import com.mongoose.clean.data.model.user.Result
import com.mongoose.clean.data.model.user.UserEntity

/**
 *  UserDomainModel.kt
 *
 *  Created by jangwon on 2021/03/05
 *
 */

data class UserDomainModel(
    val id: String,
    val cell: String,
    val email: String,
    val name: String,
    val thumbnail: String,
) {
    companion object {
        fun mapFromSpecModel(data: Result) = UserDomainModel(
            id = data.id.value ?: "",
            cell = data.cell,
            email = data.email,
            name = "${data.name.title ?: ""} ${data.name.first ?: ""} ${data.name.last ?: ""}",
            thumbnail = data.picture.thumbnail
        )

        fun mapFromDataModel(data: UserEntity) = UserDomainModel(
            id = data.id,
            cell = data.cell,
            email = data.email,
            name = data.name,
            thumbnail = data.thumbnail
        )
    }
}