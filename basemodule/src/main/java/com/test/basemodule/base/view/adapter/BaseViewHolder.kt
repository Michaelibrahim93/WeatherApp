package com.test.basemodule.base.view.adapter

import android.content.Context
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Michael on 12/25/17.
 */
abstract class BaseViewHolder<DataType, Binding : ViewDataBinding>(protected val binding: Binding) :
    RecyclerView.ViewHolder(binding.root) {
    var onItemClickListener: OnItemClickListener<DataType>? = null
    protected var itemData: DataType? = null

    init {
        itemView.setOnClickListener { v: View? ->
            if (onItemClickListener != null)
                onItemClickListener!!.onItemClick(v, itemData)
        }
    }

    fun bind(itemData: DataType) {
        this.itemData = itemData
        onBindView(itemData)
    }

    protected abstract fun onBindView(itemData: DataType)

    protected val context: Context
        get() = itemView.context


}