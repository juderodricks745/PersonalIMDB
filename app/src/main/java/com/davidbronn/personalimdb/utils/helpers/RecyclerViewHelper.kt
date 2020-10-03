package com.davidbronn.personalimdb.utils.helpers

import android.content.Context
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.davidbronn.personalimdb.utils.decorations.SpacesItemDecoration

/**
 * Created by Jude on 12/September/2020
 */
fun RecyclerView.spaceDecoration(@DimenRes id: Int) {
    addItemDecoration(SpacesItemDecoration(this.context.resources.getDimensionPixelSize(id)))
}

fun RecyclerView.gridSpan(context: Context, count: Int) {
    this.layoutManager = GridLayoutManager(context, count, GridLayoutManager.VERTICAL, false)
}