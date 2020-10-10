package com.davidbronn.personalimdb.utils.helpers

import android.view.View

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