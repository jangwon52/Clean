package com.mongoose.clean.data.source.remote

import com.mongoose.clean.data.error.DataException
import com.mongoose.clean.data.model.MainDataModel
import com.mongoose.clean.data.model.user.Result
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

    override fun getUser(page: Int): Single<List<Result>> {
        return Single.create { emitter ->
            api.getUsers(page)
                .subscribe({ response ->
                    if (response.body() == null) {
                        emitter.onError(DataException.UnknownException("getUser"))
                        return@subscribe
                    }
                    emitter.onSuccess(response.body()?.results)
                }, {
                    emitter.onError(DataException.UnknownException("getUser"))
                })
        }
    }
}