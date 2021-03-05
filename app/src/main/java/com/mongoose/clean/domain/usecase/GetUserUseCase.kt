package com.mongoose.clean.domain.usecase

import com.mongoose.clean.data.DataResult
import com.mongoose.clean.domain.model.UserDomainModel
import io.reactivex.rxjava3.core.Observable

/**
 *  GetUserUseCase.kt
 *
 *  Created by jangwon on 2021/03/05
 *
 */

interface GetUserUseCase {
    operator fun invoke(page: Int): Observable<DataResult<UserDomainModel>>
}