package com.mongoose.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mongoose.clean.R
import com.mongoose.clean.ServiceLocator

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels {
        ServiceLocator.getInstance().viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel.get()
    }
}