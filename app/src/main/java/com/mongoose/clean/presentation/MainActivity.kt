package com.mongoose.clean.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.mongoose.clean.R
import com.mongoose.clean.ServiceLocator
import com.mongoose.clean.databinding.ActivityMainBinding
import com.mongoose.framework.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val mainViewModel: MainViewModel by viewModels {
        ServiceLocator.getInstance(this).viewModelFactory
    }

    private val pagingAdapter by lazy {
        UserListPagingAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = mainViewModel
        binding.lifecycleOwner = this

        mainViewModel.getUserPaging()

        setAdapter()
        setObservers()
    }

    private fun setAdapter() {
        binding.rvUser.adapter = pagingAdapter
    }

    private fun setObservers() {
        mainViewModel.userPagingList.observe(this, {
            pagingAdapter.submitData(lifecycle, it)
        })
    }
}