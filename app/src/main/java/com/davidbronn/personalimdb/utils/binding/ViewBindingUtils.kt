package com.davidbronn.personalimdb.utils.binding

import android.graphics.Bitmap
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.utils.helpers.visible
import com.davidbronn.personalimdb.utils.misc.MovieConstants

/**
 * Created by Jude on 12/January/2020
 */
object ViewBindingUtils {

    private const val IMAGE_URL = "imageUrl"
    private const val IMAGE_TITLE = "imageTitle"
    private const val VIEW_VISIBILITY = "visibleGone"
    private const val IMAGE_BACKDROP = "imageBackDrop"
    private const val IMAGE_LISTENER = "imageListener"

    private const val VISIBILITY_ANIMATION = "visibilityAnimation"

    @JvmStatic
    @BindingAdapter(IMAGE_URL)
    fun ImageView.setImagePosterUrl(url: String?) {
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
            .placeholder(R.drawable.img_movie_placeholder)
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

    @JvmStatic
    @BindingAdapter(VIEW_VISIBILITY)
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter(VISIBILITY_ANIMATION)
    fun showWithAnimation(view: CardView, show: Boolean) {
        if (show) {
            view.visible()
        }
    }
}