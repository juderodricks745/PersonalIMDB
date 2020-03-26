package com.davidbronn.personalimdb.utils.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.utils.misc.MovieUrls

/**
 * Created by Jude on 12/January/2020
 */
object ImageViewBindingUtils {

    private const val IMAGE_URL = "imageUrl"
    private const val IMAGE_BACKDROP = "imageBackDrop"

    @JvmStatic
    @BindingAdapter(IMAGE_URL)
    fun ImageView.setImagePosterUrl(url: String?) {
        Glide.with(this)
            .load(MovieUrls.POSTER_200 + url)
            .placeholder(R.drawable.img_movie_placeholder)
            .error(R.drawable.img_movie_placeholder)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter(IMAGE_BACKDROP)
    fun ImageView.setImageBackDropUrl(url: String?) {
        Glide.with(this)
            .load(MovieUrls.POSTER_500 + url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }
}