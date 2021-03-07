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
    override fun invoke(page: Int): Observable<DataResult<List<UserDomainModel>>> {
        return mainRepository.getUser(page).map {
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