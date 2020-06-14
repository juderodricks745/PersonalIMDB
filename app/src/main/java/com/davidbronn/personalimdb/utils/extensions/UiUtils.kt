package com.davidbronn.personalimdb.utils.extensions

import android.view.View

fun toVisibility(constraint: Boolean): Int = if (constraint) {
    View.VISIBLE
} else {
    View.GONE
}