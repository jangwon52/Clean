package com.mongoose.clean.data.source.remote

import com.mongoose.clean.data.error.DataException
import com.mongoose.clean.data.model.MainDataModel
import com.mongoose.clean.data.model.user.UserResponse
import com.mongoose.clean.data.source.MainDataSourceInterface
import io.reactivex.rxjava3.core.Single

/**
 *  RemoteDataSourceImpl.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

class RemoteDataSourceImpl(private val api: UserApi) : MainDataSourceInterface {

    override fun get(): Single<MainDataModel> {
        return Single.create { emitter ->
            emitter.onError(DataException.UnknownException("remote"))
        }
    }

    override fun getUser(page: Int): Single<UserResponse> {
        return Single.create { emitter ->
            api.getUsers(page)
                .subscribe({ response ->
                    emitter.onSuccess(response.body())
                }, {
                    emitter.onError(DataException.UnknownException("getUser"))
                })
        }
    }
}