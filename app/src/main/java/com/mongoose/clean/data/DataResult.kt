package com.mongoose.clean.data

/**
 *  DataResult.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val exception: Exception) : DataResult<Nothing>()
    object Loading : DataResult<Nothing>()
}