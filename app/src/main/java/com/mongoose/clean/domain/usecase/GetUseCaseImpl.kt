package com.mongoose.clean.domain.usecase

import com.mongoose.clean.data.DataResult
import com.mongoose.clean.domain.MainRepositoryInterface
import com.mongoose.clean.domain.error.DomainException
import com.mongoose.clean.domain.model.MainDomainModel
import io.reactivex.rxjava3.core.Observable

/**
 *  GetUseCaseImpl.kt
 *
 *  Created by jangwon on 2021/03/04
 *
 */

class GetUseCaseImpl(
    private val mainRepository: MainRepositoryInterface,
) : GetUseCase {
    override fun invoke(needRefresh: Boolean): Observable<DataResult<MainDomainModel>> {
        return mainRepository.get(needRefresh).map {
            when (it) {
                is DataResult.Success -> {
                    DataResult.Success(MainDomainModel.mapFromDataModel(it.data))
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