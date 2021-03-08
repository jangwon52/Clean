package com.mongoose.clean.data.source.remote

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import com.mongoose.clean.data.error.DataException
import com.mongoose.clean.data.model.MainDataModel
import com.mongoose.clean.data.model.user.Result
import com.mongoose.clean.data.model.user.UserResponse
import com.mongoose.clean.data.source.MainDataSourceInterface
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

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

    override fun getUserPaging(): RxPagingSource<Int, Result> {
        return PageKeyedPagingSource(api)
    }

    inner class PageKeyedPagingSource(private val api: UserApi) : RxPagingSource<Int, Result>() {
        override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Result>> {
            val position = params.key ?: 1

            return api.getUsers(position)
                .subscribeOn(Schedulers.io())
                .map {
                    it.body() ?: error(DataException.UnknownException("paging"))
                }
                .map { toLoadResult(it, position) }
                .onErrorReturn { LoadResult.Error(it) }
        }

        override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
            return state.anchorPosition
        }

        private fun toLoadResult(data: UserResponse, position: Int): LoadResult<Int, Result> {
            return LoadResult.Page(
                data = data.results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1
            )
        }
    }
}