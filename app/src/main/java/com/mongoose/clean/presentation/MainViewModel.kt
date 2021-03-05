package com.mongoose.clean.presentation

import android.util.Log
import com.mongoose.clean.domain.usecase.GetUseCase
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
) : BaseViewModel() {

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
                onNext = {

                },
                onError = {

                }
            ).addTo(disposeBag)
    }
}