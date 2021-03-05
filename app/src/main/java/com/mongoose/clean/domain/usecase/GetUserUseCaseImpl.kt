package com.mongoose.clean.domain.usecase

import com.mongoose.clean.data.DataResult
import com.mongoose.clean.domain.MainRepositoryInterface
import com.mongoose.clean.domain.error.DomainException
import com.mongoose.clean.domain.model.UserDomainModel
import io.reactivex.rxjava3.core.Observable

/**
 *  GetUserUseCaseImpl.kt
 *
 *  Created by jangwon on 2021/03/05
 *
 */

class GetUserUseCaseImpl(
    private val mainRepository: MainRepositoryInterface,
) : GetUserUseCase {
    override fun invoke(page: Int): Observable<DataResult<UserDomainModel>> {
        return mainRepository.getUser(page).map {
            when (it) {
                is DataResult.Success -> {
                    DataResult.Success(UserDomainModel.mapFromDataModel(it.data))
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