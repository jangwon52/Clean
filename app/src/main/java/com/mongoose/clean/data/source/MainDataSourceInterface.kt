package com.mongoose.clean.data.source

import androidx.paging.rxjava3.RxPagingSource
import com.mongoose.clean.data.model.MainDataModel
import com.mongoose.clean.data.model.user.Result
import io.reactivex.rxjava3.core.Single

/**
 *  MainDataSourceInterface.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

interface MainDataSourceInterface {
    fun get(): Single<MainDataModel>
    fun getUser(page: Int): Single<List<Result>>
    fun getUserPaging(): RxPagingSource<Int, Result>
}