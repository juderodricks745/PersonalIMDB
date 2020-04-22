package com.davidbronn.personalimdb.utils.binding

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.utils.misc.MovieConstants
import timber.log.Timber

/**
 * Created by Jude on 12/January/2020
 */
object ImageViewBindingUtils {

    private const val IMAGE_URL = "imageUrl"
    private const val IMAGE_BACKDROP = "imageBackDrop"

    private const val IMAGE_TITLE = "imageTitle"

    @JvmStatic
    @BindingAdapter(IMAGE_URL)
    fun ImageView.setImagePosterUrl(url: String?) {
        Timber.i("Url => ${MovieConstants.Urls.POSTER_200 + url}")
        Glide.with(this)
            .load(MovieConstants.Urls.POSTER_200 + url)
            .placeholder(R.drawable.img_movie_placeholder)
            .error(R.drawable.img_movie_placeholder)
            .into(this)
    }

    @JvmStatic
    @BindingAdapter(IMAGE_BACKDROP)
    fun ImageView.setImageBackDropUrl(url: String?) {
        Glide.with(this)
            .load(MovieConstants.Urls.POSTER_500 + url)
            .transition(DrawableTransitionOptions.withCrossFade(500))
            .into(this)
    }

    @JvmStatic
    @BindingAdapter(IMAGE_URL, IMAGE_TITLE)
    fun ImageView.setImagePosterUrlWithTitle(url: String?, title: AppCompatTextView) {
        Glide.with(context)
            .asBitmap()
            .load(MovieConstants.Urls.POSTER_200 + url)
            .apply(RequestOptions().centerCrop())
            .into(object : BitmapImageViewTarget(this) {
                override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                    super.onResourceReady(bitmap, transition)
                    Palette.from(bitmap).generate { palette ->
                        val color = palette!!.getVibrantColor(
                            ContextCompat.getColor(
                                this@setImagePosterUrlWithTitle.context,
                                R.color.black_translucent_60
                            )
                        )
                        title.setBackgroundColor(color)
                    }
                }
            })
    }
}