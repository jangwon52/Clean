package com.mongoose.clean.data.error

/**
 *  DataException.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

sealed class DataException(errorMessage: String) : Exception(errorMessage) {

    class UnknownException(errorMessage: String) : DataException(errorMessage)
}