package com.mongoose.clean.data.source.local

import com.mongoose.clean.data.model.MainDataModel
import com.mongoose.clean.data.source.MainDataSourceInterface
import io.reactivex.rxjava3.core.Single

/**
 *  LocalDataSourceImpl.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

class LocalDataSourceImpl : MainDataSourceInterface {

    override fun get(): Single<MainDataModel> {
        return Single.just(MainDataModel("local"))
    }
}