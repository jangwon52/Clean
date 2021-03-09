package com.mongoose.clean.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.rxjava3.RxRemoteMediator
import com.mongoose.clean.data.model.RemoteKeys
import com.mongoose.clean.data.model.user.UserEntity
import com.mongoose.clean.data.source.MainDataSourceInterface
import com.mongoose.clean.data.source.local.RemoteKeysDao
import com.mongoose.clean.data.source.local.UsersDao
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import java.io.InvalidObjectException

/**
 *  UserRemoteMediator.kt
 *
 *  Created by jangwon on 2021/03/09
 *
 */

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val remoteDataSource: MainDataSourceInterface,
    private val usersDao: UsersDao,
    private val remoteKeysDao: RemoteKeysDao,
) : RxRemoteMediator<Int, UserEntity>() {

    override fun loadSingle(loadType: LoadType, state: PagingState<Int, UserEntity>): Single<MediatorResult> {
        return Single.create { emitter ->
            // The network load method takes an optional [String] parameter. For every page
            // after the first, we pass the [String] token returned from the previous page to
            // let it continue from where it left off. For REFRESH, pass `null` to load the
            // first page.

            var loadKey = INVALID_PAGE

            when (loadType) {
                LoadType.REFRESH -> MediatorResult.Success(endOfPaginationReached = false)
                // In this example, we never need to prepend, since REFRESH will always load the
                // first page in the list. Immediately return, reporting end of pagination.
                LoadType.PREPEND -> emitter.onSuccess(MediatorResult.Success(endOfPaginationReached = true))
                // Query remoteKeyDao for the next RemoteKey.
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                        ?: throw InvalidObjectException("Result is empty")

                    loadKey = remoteKeys.nextKey ?: INVALID_PAGE
                }
            }

            if (loadKey == INVALID_PAGE) {
                emitter.onSuccess(MediatorResult.Success(endOfPaginationReached = false))
                return@create
            }

            remoteDataSource.getUser(loadKey)
                .subscribeBy(
                    onError = {
                        emitter.onError(it)
                    },
                    onSuccess = {
                        val entityList = it.map { UserEntity.mapFromSpec(it) }

                        insertToDb(loadKey, loadType, entityList)

                        emitter.onSuccess(MediatorResult.Success(endOfPaginationReached = entityList.isEmpty()))
                    }
                )
        }
    }

    private fun getRemoteKeyForLastItem(state: PagingState<Int, UserEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { entitiy ->
            remoteKeysDao.remoteKeysId(entitiy.id)
        }
    }

    private fun getRemoteKeyForFirstItem(state: PagingState<Int, UserEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { entitiy ->
            remoteKeysDao.remoteKeysId(entitiy.id)
        }
    }

    private fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, UserEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.remoteKeysId(id)
            }
        }
    }

    @Suppress("DEPRECATION")
    private fun insertToDb(page: Int, loadType: LoadType, data: List<UserEntity>) {

        if (loadType == LoadType.REFRESH) {
            remoteKeysDao.clearRemoteKeys()
            usersDao.deleteAll()
        }

        val prevKey = if (page == 1) null else page - 1
        val nextKey = if (data.isEmpty()) null else page + 1
        val keys = data.map {
            RemoteKeys(id = it.id, prevKey = prevKey, nextKey = nextKey)
        }
        remoteKeysDao.insertAll(keys)
        usersDao.insertAll(data)
    }

    companion object {
        const val INVALID_PAGE = -1
    }
}