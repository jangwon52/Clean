package com.mongoose.clean

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Created by mongoose on 2021/02/23

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel()
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
