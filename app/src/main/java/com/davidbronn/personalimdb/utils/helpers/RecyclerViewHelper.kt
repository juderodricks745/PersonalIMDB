package com.davidbronn.personalimdb.utils.helpers

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Jude on 12/September/2020
 */
fun RecyclerView.gridSpan(context: Context, count: Int) {
    this.layoutManager = GridLayoutManager(context, count, GridLayoutManager.VERTICAL, false)
}