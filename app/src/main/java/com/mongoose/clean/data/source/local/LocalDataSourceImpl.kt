package com.mongoose.clean.data.source.local

import androidx.paging.rxjava3.RxPagingSource
import com.mongoose.clean.data.model.MainDataModel
import com.mongoose.clean.data.model.user.Result
import com.mongoose.clean.data.model.user.UserResponse
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
        TODO("Not yet implemented")
    }

    override fun getUser(page: Int?): Single<List<Result>> {
        TODO("Not yet implemented")
    }

    override fun getUserPaging(): RxPagingSource<Int, Result> {
        TODO("Not yet implemented")
    }
}