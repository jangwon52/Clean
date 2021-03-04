package com.mongoose.clean.data.source

import com.mongoose.clean.data.model.MainDataModel
import io.reactivex.rxjava3.core.Single

/**
 *  MainDataSourceInterface.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

interface MainDataSourceInterface {
    fun get(): Single<MainDataModel>
}