package com.mongoose.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mongoose.clean.R
import com.mongoose.clean.ServiceLocator
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    val compositeDisposable = CompositeDisposable()

    private val mainViewModel: MainViewModel by viewModels {
        ServiceLocator.getInstance().viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ServiceLocator.getInstance().api.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                println(response.headers())
            },{
                it.printStackTrace()
            }).let {
                compositeDisposable.add(it)
            }

        mainViewModel.get()
    }
}