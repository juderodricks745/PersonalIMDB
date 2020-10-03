package com.davidbronn.personalimdb.utils.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.davidbronn.personalimdb.R
import com.davidbronn.personalimdb.utils.misc.MovieConstants

fun toVisibility(constraint: Boolean): Int = if (constraint) {
    View.VISIBLE
} else {
    View.GONE
}

fun ImageView.urlWithListener(url: String?, body: () -> Unit) {
    Glide.with(this)
        .load(MovieConstants.Urls.POSTER_200 + url)
        .placeholder(R.drawable.img_movie_placeholder)
        .error(R.drawable.img_movie_placeholder)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                body()
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                body()
                return false
            }
        }).into(this)
}