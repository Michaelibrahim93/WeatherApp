package com.test.basemodule.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.initLinear(context: Context, adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = LinearLayoutManager(context)
    this.adapter = adapter
}