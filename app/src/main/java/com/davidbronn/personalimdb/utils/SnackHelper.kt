package com.davidbronn.personalimdb.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.withSnack(message: String, isLong: Boolean = true) {
    if (message.isNotBlank()) {
        Snackbar.make(
            this, message,
            if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT)
            .show()
    }
}

fun View.withActionSnack(message: String, isLong: Boolean, actionText: String, action: () -> Unit) {
    if (message.isNotBlank()) {
        Snackbar.make(this, message,
            if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT)
            .setAction(actionText) {
                action()
            }
            .show()
    }
}