package com.mongoose.clean.domain.usecase

import com.mongoose.clean.data.DataResult
import com.mongoose.clean.domain.model.MainDomainModel
import io.reactivex.rxjava3.core.Observable

/**
 *  GetUseCase.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

interface GetUseCase {
    operator fun invoke(needRefresh: Boolean): Observable<DataResult<MainDomainModel>>
}