package com.mongoose.clean.domain

import androidx.paging.PagingData
import com.mongoose.clean.data.DataResult
import com.mongoose.clean.data.model.MainDataModel
import com.mongoose.clean.data.model.user.Result
import com.mongoose.clean.data.model.user.UserEntity
import io.reactivex.rxjava3.core.Observable

/**
 *  MainRepositoryInterface.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

interface MainRepositoryInterface {
    fun get(needRefresh: Boolean): Observable<DataResult<MainDataModel>>
    fun getUser(page: Int): Observable<DataResult<List<Result>>>
    fun getUserPaging(pageSize: Int): Observable<DataResult<PagingData<UserEntity>>>
}