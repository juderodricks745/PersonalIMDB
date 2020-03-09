package com.davidbronn.personalimdb.utils.helpers

import android.app.Activity
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat

/**
 * Created by Jude on 07/March/2020
 */

fun Activity.sceneAnimationWith(view: View): ActivityOptionsCompat {
    return ActivityOptionsCompat.makeSceneTransitionAnimation(
        this, view, ViewCompat.getTransitionName(view)!!
    )
}