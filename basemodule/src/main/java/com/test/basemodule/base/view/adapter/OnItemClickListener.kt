package com.test.basemodule.base.view.adapter

import android.view.View

/**
 * Created by Michael on 12/25/17.
 */
interface OnItemClickListener<K> {
    fun onItemClick(view: View?, item: K?)
}