package com.davidbronn.personalimdb.utils.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.utils.MovieUrls

/**
 * Created by Jude on 12/January/2020
 */
object ImageViewBindingUtils {

    private const val IMAGE_URL = "imageUrl"
    private const val IMAGE_BACKDROP = "image_backdrop"

    @JvmStatic
    @BindingAdapter(IMAGE_URL)
    fun ImageView.setDefaultLoader(url: String?) {
        if (!url.isNullOrBlank()) {
            this.load(MovieUrls.POSTER_200 + url) {
                placeholder(R.drawable.movie_placeholder)
                crossfade(true)
            }
        }
    }

    @JvmStatic
    @BindingAdapter(IMAGE_BACKDROP)
    fun ImageView.setImageBackDrop(url: String?) {
        if (!url.isNullOrBlank()) {
            this.load(MovieUrls.POSTER_500 + url) {
                crossfade(true)
            }
        }
    }
}