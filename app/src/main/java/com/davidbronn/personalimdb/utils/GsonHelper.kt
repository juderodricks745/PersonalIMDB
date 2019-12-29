package com.davidbronn.personalimdb.utils

import com.davidbronn.personalimdb.AppController

inline fun <reified T> String.jsonify(): T? {
    return AppController.requireGson()?.fromJson(this, T::class.java)
}

fun <T> stringify(list: List<T>): String? {
    return AppController.requireGson()?.toJson(list)
}