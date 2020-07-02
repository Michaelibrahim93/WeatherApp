package com.test.basemodule.base.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.test.basemodule.utils.ReflectionUtils
import com.test.basemodule.utils.Utils.equals
import java.util.*

/**
 * Created by Michael on 12/25/17.
 */
abstract class AbstractRecyclerAdapter<DataType, Binding : ViewDataBinding, ViewHolder : BaseViewHolder<DataType, Binding>>
    : ListAdapter<DataType, ViewHolder> {
    protected var mData: List<DataType> = ArrayList()
    protected var clickListener: OnItemClickListener<DataType>? =
        null
    protected abstract val viewRes: Int

    constructor(
        clickListener: OnItemClickListener<DataType>? = null
        , diffCallback: DiffUtil.ItemCallback<DataType>? = null)
            : super(createConfiguration(diffCallback)) {
        this.clickListener = clickListener
    }

    abstract fun onCreateDefaultViewHolder(viewGroup: ViewGroup?, viewType: Int, binding: Binding): ViewHolder
    abstract fun onViewHolderBond(k: ViewHolder, position: Int)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: Binding = DataBindingUtil.inflate(layoutInflater, viewRes, parent, false)
        val viewHolder = onCreateDefaultViewHolder(parent, viewType, binding)
        viewHolder.onItemClickListener = clickListener
        return viewHolder
    }

    override fun onBindViewHolder(k: ViewHolder, position: Int) {
        k.bind(mData[position])
        onViewHolderBond(k, position)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    fun setData(mData: List<DataType>) {
        this.mData = mData
        notifyDataSetChanged()
    }

    companion object {
        @JvmStatic
        private fun <E> createConfiguration(diffUtil: DiffUtil.ItemCallback<E>?): AsyncDifferConfig<E> {
            val actualDiffUtil: DiffUtil.ItemCallback<E> = diffUtil?: defaultDifUtils()
            return AsyncDifferConfig.Builder(actualDiffUtil).build()
        }

        @JvmStatic
        private fun <E> defaultDifUtils(): DiffUtil.ItemCallback<E> {
            return object : DiffUtil.ItemCallback<E>() {
                override fun areItemsTheSame(oldItem: E, newItem: E): Boolean {
                    return equals(oldItem, newItem, true)
                }

                override fun areContentsTheSame(oldItem: E, newItem: E): Boolean {
                    return ReflectionUtils.isIdentical(oldItem, newItem)
                }
            }
        }
    }
}