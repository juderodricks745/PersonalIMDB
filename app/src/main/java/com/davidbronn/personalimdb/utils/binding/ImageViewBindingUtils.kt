package com.davidbronn.personalimdb.utils.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
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
        if (!url.isNullOrBlank()) {
            this.load(MovieUrls.POSTER_200 + url) {
                crossfade(true)
                placeholder(R.drawable.movie_placeholder)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(IMAGE_BACKDROP)
    fun ImageView.setImageBackDropUrl(url: String?) {
        if (!url.isNullOrBlank()) {
            this.load(MovieUrls.POSTER_500 + url) {
                crossfade(true)
            }
        }
    }
}