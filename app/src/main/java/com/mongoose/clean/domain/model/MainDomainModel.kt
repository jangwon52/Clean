package com.mongoose.clean.domain.model

import com.mongoose.clean.data.model.MainDataModel

/**
 *  MainDomainModel.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

data class MainDomainModel(
    val id: String,
) {
    companion object {
        fun mapFromDataModel(data: MainDataModel) = MainDomainModel(
            id = data.id
        )
    }
}