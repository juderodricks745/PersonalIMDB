package com.davidbronn.personalimdb.utils.helpers

import android.transition.Fade
import android.transition.Transition
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation


/**
 * Created by Jude on 07/March/2020
 */

/**
 * Sets the view visible
 */
fun View.visible() {
    this.visibility = View.VISIBLE
}

/**
 * Sets the view as gone
 */
fun View.gone() {
    this.visibility = View.GONE
}

/**
 * Scales the view from center; startAnimation & visibility are already present
 * other attributes like duration, interpolator can be set.
 */
fun View.viewCenterScale(func: ScaleAnimation.() -> Unit) {
    ScaleAnimation(
        0f, 1f,
        0f, 1f,
        Animation.RELATIVE_TO_PARENT, 0.5f,
        Animation.RELATIVE_TO_PARENT, 0.5f
    ).apply {
        func()
        this@viewCenterScale.startAnimation(this)
        this@viewCenterScale.visibility = View.VISIBLE
    }

}

fun fadeEnterTransitionWithExclusion(faded: Fade.() -> Unit): Transition {
    return Fade().apply {
        faded()
    }
}