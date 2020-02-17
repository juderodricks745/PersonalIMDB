package com.davidbronn.personalimdb.ui.annotations

import androidx.annotation.IntDef

/**
 * Created by Jude on 06/February/2020
 */
@Retention(AnnotationRetention.SOURCE)
@IntDef(
    value = [
        MovieType.MOVIES_LATEST,
        MovieType.MOVIES_NOW_PLAYING,
        MovieType.MOVIES_POPULAR,
        MovieType.MOVIES_TOP_RATED,
        MovieType.MOVIES_UPCOMING
    ]
)
annotation class MovieType {
    companion object {
        const val MOVIES_LATEST = 0
        const val MOVIES_NOW_PLAYING = 1
        const val MOVIES_POPULAR = 2
        const val MOVIES_TOP_RATED = 3
        const val MOVIES_UPCOMING = 4
    }
}