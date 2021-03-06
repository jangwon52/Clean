package com.mongoose.clean.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.mongoose.clean.data.DataResult
import com.mongoose.clean.domain.model.UserDomainModel
import com.mongoose.clean.domain.usecase.GetUseCase
import com.mongoose.clean.domain.usecase.GetUserPagingUseCase
import com.mongoose.clean.domain.usecase.GetUserUseCase
import com.mongoose.framework.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

// Created by mongoose on 2021/02/23

class MainViewModel(
    private val getUseCase: GetUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val getUserPagingUseCase: GetUserPagingUseCase,
) : BaseViewModel() {

    private val _userList = MutableLiveData<List<UserDomainModel>>()
    val userList: LiveData<List<UserDomainModel>> = _userList

    private val _userPagingList = MutableLiveData<PagingData<UserDomainModel>>()
    val userPagingList: LiveData<PagingData<UserDomainModel>> = _userPagingList

    fun get() {
        getUseCase.invoke(false)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = {
                    Log.d("onNext!!", it.toString())
                },
                onError = {
                    Log.d("onError!!", it.toString())
                }
            ).addTo(disposeBag)
    }

    fun getUser() {
        getUserUseCase.invoke(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = ::handleOnNext,
                onError = {
                }
            ).addTo(disposeBag)
    }

    fun getUserPaging() {
        getUserPagingUseCase.invoke(30)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = ::handlePagingOnNext,
                onError = {
                }
            ).addTo(disposeBag)
    }

    private fun handleOnNext(result: DataResult<List<UserDomainModel>>) {
        when (result) {
            is DataResult.Success -> {
                _userList.value = result.data
            }
            is DataResult.Loading -> {
            }
            is DataResult.Error -> {
            }
        }
    }

    private fun handlePagingOnNext(result: DataResult<PagingData<UserDomainModel>>) {
        when (result) {
            is DataResult.Success -> {
                _userPagingList.value = result.data
            }
            is DataResult.Loading -> {
            }
            is DataResult.Error -> {
            }
        }
    }
}