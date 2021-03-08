package com.mongoose.clean

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mongoose.clean.domain.usecase.GetUseCase
import com.mongoose.clean.domain.usecase.GetUserPagingUseCase
import com.mongoose.clean.domain.usecase.GetUserUseCase
import com.mongoose.clean.presentation.MainViewModel

// Created by mongoose on 2021/02/23

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
    private val getUseCase: GetUseCase,
    private val getUserUsecase: GetUserUseCase,
    private val getUserPagingUseCase: GetUserPagingUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(getUseCase, getUserUsecase, getUserPagingUseCase)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
