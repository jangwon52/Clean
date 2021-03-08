package com.mongoose.clean.domain.usecase

import androidx.paging.PagingData
import com.mongoose.clean.data.DataResult
import com.mongoose.clean.domain.model.UserDomainModel
import io.reactivex.rxjava3.core.Observable

/**
 *  GetUserPagingUseCase.kt
 *
 *  Created by jangwon on 2021/03/08
 *
 */

interface GetUserPagingUseCase {
    operator fun invoke(pageSize: Int): Observable<DataResult<PagingData<UserDomainModel>>>
}