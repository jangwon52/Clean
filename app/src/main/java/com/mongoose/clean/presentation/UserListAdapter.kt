package com.mongoose.clean.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.mongoose.clean.databinding.LayoutItemUserBinding
import com.mongoose.clean.domain.model.UserDomainModel
import com.mongoose.framework.BaseBindingViewHolder
import com.mongoose.framework.BaseDiffCallbackAdapter

// Created by mongoose on 2021/03/07

class UserListAdapter :
    BaseDiffCallbackAdapter<UserDomainModel, LayoutItemUserBinding>(COMPARATOR) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseBindingViewHolder<LayoutItemUserBinding> {
        return UserItemViewHolder(
            LayoutItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: BaseBindingViewHolder<LayoutItemUserBinding>,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<UserDomainModel>() {
            override fun areItemsTheSame(
                oldItem: UserDomainModel,
                newItem: UserDomainModel
            ): Boolean {
                return oldItem.cell == newItem.cell
            }

            override fun areContentsTheSame(
                oldItem: UserDomainModel,
                newItem: UserDomainModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}