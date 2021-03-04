package com.mongoose.clean.domain.error

import com.mongoose.clean.data.error.DataException

/**
 *  DomainException.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

sealed class DomainException(errorMessage: String) : Exception(errorMessage) {

    class UnknownException(errorMessage: String) : DomainException(errorMessage)

    companion object {
        fun mapFromDataException(data: Exception): DomainException {
            return when (data) {
                is DataException.UnknownException -> UnknownException(data.message ?: "")
                else -> UnknownException(data.message ?: "")
            }
        }
    }
}