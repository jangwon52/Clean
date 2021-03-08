package com.mongoose.clean.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.mongoose.clean.data.DataResult
import com.mongoose.clean.domain.MainRepositoryInterface
import com.mongoose.clean.domain.error.DomainException
import com.mongoose.clean.domain.model.UserDomainModel
import io.reactivex.rxjava3.core.Observable

/**
 *  GetUserPagingUseCaseImpl.kt
 *
 *  Created by jangwon on 2021/03/08
 *
 */

class GetUserPagingUseCaseImpl(
    private val mainRepository: MainRepositoryInterface,
) : GetUserPagingUseCase {
    override fun invoke(pageSize: Int): Observable<DataResult<PagingData<UserDomainModel>>> {
        return mainRepository.getUserPaging(pageSize).map {
            when (it) {
                is DataResult.Success -> {
                    val list = it.data.map { result ->
                        UserDomainModel.mapFromDataModel(result)
                    }
                    DataResult.Success(list)
                }
                is DataResult.Loading -> {
                    DataResult.Loading
                }
                is DataResult.Error -> {
                    DataResult.Error(DomainException.mapFromDataException(it.exception))
                }
            }
        }
    }
}