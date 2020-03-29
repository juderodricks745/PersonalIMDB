package com.davidbronn.personalimdb.utils.helpers

import android.view.View
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation


/**
 * Created by Jude on 07/March/2020
 */

fun View.scale() {
    ScaleAnimation(
        0f, 1f,
        0f, 1f,
        Animation.RELATIVE_TO_PARENT, 0.5f,
        Animation.RELATIVE_TO_PARENT, 0.5f
    ).apply {
        duration = 600
        interpolator = OvershootInterpolator()
        this@scale.startAnimation(this)
        this@scale.visibility = View.VISIBLE
    }
}