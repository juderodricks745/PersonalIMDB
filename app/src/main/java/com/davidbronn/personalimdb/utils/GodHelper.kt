package com.davidbronn.personalimdb.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast

fun initPrefs(name: String, context: Context): SharedPreferences {
    return context.getSharedPreferences(name, Context.MODE_PRIVATE)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}