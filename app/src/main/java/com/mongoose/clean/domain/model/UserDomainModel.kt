package com.mongoose.clean.domain.model

import com.mongoose.clean.data.model.user.UserResponse

/**
 *  UserDomainModel.kt
 *
 *  Created by jangwon on 2021/03/05
 *
 */

data class UserDomainModel(
    val id: String,
) {
    companion object {
        fun mapFromDataModel(data: UserResponse) = UserDomainModel(
            id = data.info.seed
        )
    }
}