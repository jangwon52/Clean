package com.mongoose.clean.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 *  ImageViewExt.kt
 *
 *  Created by jangwon on 2021/03/08
 *
 */

@BindingAdapter("loadCircleImage")
fun ImageView.loadCircleImage(
    imageUrl: String?,
) {
    Glide.with(context)
        .load(imageUrl ?: "")
        .transition(DrawableTransitionOptions.withCrossFade())
        .circleCrop()
        .into(this)
}