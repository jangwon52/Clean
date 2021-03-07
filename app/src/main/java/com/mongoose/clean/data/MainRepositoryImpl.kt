package com.mongoose.clean.data

import com.mongoose.clean.data.model.MainDataModel
import com.mongoose.clean.data.model.user.Result
import com.mongoose.clean.data.source.MainDataSourceInterface
import com.mongoose.clean.domain.MainRepositoryInterface
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy

/**
 *  MainRepositoryImpl.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

class MainRepositoryImpl(
    private val localDataSource: MainDataSourceInterface,
    private val remoteDataSource: MainDataSourceInterface,
) : MainRepositoryInterface {
    override fun get(needRefresh: Boolean): Observable<DataResult<MainDataModel>> {
        return Observable.create { emitter ->
            emitter.onNext(DataResult.Loading)

            if (needRefresh) {
                remoteDataSource.get()
                    .doFinally { emitter.onComplete() }
                    .subscribeBy(
                        onError = {
                            emitter.onError(
                                DataResult.Error(
                                    it as? Exception
                                        ?: return@subscribeBy
                                ).exception
                            )
                        },
                        onSuccess = {
                            emitter.onNext(DataResult.Success(it))
                        }
                    )
                return@create
            }

            localDataSource.get()
                .doFinally { emitter.onComplete() }
                .subscribeBy(
                    onError = {
                        emitter.onError(
                            DataResult.Error(
                                it as? Exception
                                    ?: return@subscribeBy
                            ).exception
                        )
                    },
                    onSuccess = { emitter.onNext(DataResult.Success(it)) }
                )
        }
    }

    override fun getUser(page: Int): Observable<DataResult<List<Result>>> {
        return Observable.create { emitter ->
            emitter.onNext(DataResult.Loading)

            remoteDataSource.getUser(page)
                .doFinally { emitter.onComplete() }
                .subscribeBy(
                    onError = {
                        emitter.onError(
                            DataResult.Error(
                                it as? Exception
                                    ?: return@subscribeBy
                            ).exception
                        )
                    },
                    onSuccess = {
                        emitter.onNext(DataResult.Success(it))
                    }
                )
        }
    }
}