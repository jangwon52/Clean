package com.mongoose.clean.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import com.mongoose.clean.databinding.LayoutItemUserBinding
import com.mongoose.clean.domain.model.UserDomainModel
import com.mongoose.clean.presentation.UserListAdapter.Companion.COMPARATOR

/**
 *  UserListPagingAdapter.kt
 *
 *  Created by jangwon on 2021/03/08
 *
 */

class UserListPagingAdapter : PagingDataAdapter<UserDomainModel, UserItemViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): UserItemViewHolder {
        return UserItemViewHolder(
            LayoutItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: UserItemViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}