package com.mongoose.clean

import android.annotation.SuppressLint
import com.mongoose.clean.data.MainRepositoryImpl
import com.mongoose.clean.data.source.local.LocalDataSourceImpl
import com.mongoose.clean.data.source.remote.RemoteDataSourceImpl
import com.mongoose.clean.domain.usecase.GetUseCaseImpl

// Created by mongoose on 2021/03/02

open class ServiceLocator {
    val viewModelFactory by lazy {
        ViewModelFactory(GetUseCaseImpl(MainRepositoryImpl(
            LocalDataSourceImpl(),
            RemoteDataSourceImpl()
        )))
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: ServiceLocator? = null

        fun getInstance() =
            INSTANCE ?: synchronized(ServiceLocator::class.java) {
                INSTANCE ?: ServiceLocator().also {
                    INSTANCE = it
                }
            }
    }
}