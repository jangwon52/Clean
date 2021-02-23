package com.mongoose.clean

import android.annotation.SuppressLint

// Created by mongoose on 2021/02/23

open class ServiceLocator {
    val viewModelFactory by lazy {
        ViewModelFactory()
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