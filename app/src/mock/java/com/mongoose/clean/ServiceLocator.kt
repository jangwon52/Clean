package com.mongoose.clean

import android.annotation.SuppressLint
import android.content.Context
import com.mongoose.clean.data.MainRepositoryImpl
import com.mongoose.clean.data.source.local.LocalDataSourceImpl
import com.mongoose.clean.data.source.local.UsersDb
import com.mongoose.clean.data.source.remote.RemoteDataSourceImpl
import com.mongoose.clean.data.source.remote.UserApi
import com.mongoose.clean.domain.usecase.GetUseCaseImpl
import com.mongoose.clean.domain.usecase.GetUserPagingUseCaseImpl
import com.mongoose.clean.domain.usecase.GetUserUseCaseImpl
import com.mongoose.framework.network.RetrofitAdapter

// Created by mongoose on 2021/03/02

open class ServiceLocator(context: Context) {
    val viewModelFactory by lazy {
        val mainRepository = MainRepositoryImpl(
            LocalDataSourceImpl(),
            RemoteDataSourceImpl(api),
            db.usersDao(),
            db.remoteKeysDao()
        )
        ViewModelFactory(GetUseCaseImpl(mainRepository),
            GetUserUseCaseImpl(mainRepository),
            GetUserPagingUseCaseImpl(mainRepository))
    }

    val api by lazy {
        RetrofitAdapter.Builder(UserApi::class.java, UserApi.BASE_URL).apply {
            useLoggingInterceptor = true
            useGsonConverterFactory = true
            useRxAdapterFactory = true
        }.build()
    }

    val db by lazy {
        UsersDb.create(context, false)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ServiceLocator? = null

        fun getInstance(context: Context) =
            INSTANCE ?: synchronized(ServiceLocator::class.java) {
                INSTANCE ?: ServiceLocator(context).also {
                    INSTANCE = it
                }
            }
    }
}