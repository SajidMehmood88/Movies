package com.zattoo.movies.core.utils

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import androidx.databinding.BindingAdapter

object BindingAdapter {
    @JvmStatic
    @BindingAdapter("loadImage")
    fun bindLoadImage(view: AppCompatImageView, url: String?) {
        if (url != null) {
            Glide.with(view.context).load(url)
                .into(view)
        }
    }
}