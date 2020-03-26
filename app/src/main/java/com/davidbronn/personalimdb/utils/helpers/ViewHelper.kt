package com.davidbronn.personalimdb.utils.helpers

import android.view.View
import android.view.animation.Animation
import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation


/**
 * Created by Jude on 07/March/2020
 */

fun View.startOverShootAnimation(time: Long) {
    this.animate().apply {
        startDelay = time
        interpolator = OvershootInterpolator()
        alpha(1f)
        start()
    }
}

fun View.scale() {
    val anim = ScaleAnimation(
        0f, 0f, // Start and end values for the X axis scaling
        1f, 1f, // Start and end values for the Y axis scaling
        Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
        Animation.RELATIVE_TO_SELF, 1f
    ) // Pivot point of Y scaling
    anim.fillAfter = true // Needed to keep the result of the animation
    anim.duration = 1000
    this.startAnimation(anim)
}