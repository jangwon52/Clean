package com.mongoose.clean.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import androidx.paging.rxjava3.observable
import com.mongoose.clean.data.model.MainDataModel
import com.mongoose.clean.data.model.user.Result
import com.mongoose.clean.data.model.user.UserEntity
import com.mongoose.clean.data.source.MainDataSourceInterface
import com.mongoose.clean.data.source.local.RemoteKeysDao
import com.mongoose.clean.data.source.local.UsersDao
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
    private val usersDao: UsersDao,
    private val remoteKeysDao: RemoteKeysDao
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

    @OptIn(ExperimentalPagingApi::class)
    override fun getUserPaging(pageSize: Int): Observable<DataResult<PagingData<UserEntity>>> {
        return Observable.create { emitter ->
            emitter.onNext(DataResult.Loading)

            val remoteMediator = UserRemoteMediator(remoteDataSource, usersDao, remoteKeysDao)

            Pager(
                config = PagingConfig(
                    pageSize = pageSize
                ),
                remoteMediator = remoteMediator,
                pagingSourceFactory = {
                    usersDao.selectAll()
                }
            ).observable
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
                    onNext = {
                        emitter.onNext(DataResult.Success(it))
                    }
                )
        }
    }
}