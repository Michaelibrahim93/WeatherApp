package com.test.weatherapp.ui.fragments.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.test.basemodule.base.view.adapter.OnItemClickListener
import com.test.weatherapp.R
import com.test.weatherapp.databinding.ItemCityBinding
import com.test.weatherapp.ui.fragments.home.adapter.CityViewHolder
import com.test.weatherapp.vo.CityForecast

class CitySearchAdapter(
    private val onItemClickListener: OnItemClickListener<CityForecast>
): PagingDataAdapter<CityForecast, CityViewHolder>(CityItemCallback()) {

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCityBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_city, parent, false)
        val viewHolder = CityViewHolder(binding)
        viewHolder.onItemClickListener = onItemClickListener
        return viewHolder
    }

    private class CityItemCallback : DiffUtil.ItemCallback<CityForecast>() {
        override fun areItemsTheSame(oldItem: CityForecast, newItem: CityForecast): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CityForecast, newItem: CityForecast): Boolean {
            return oldItem == newItem && oldItem.isBookMarked == newItem.isBookMarked
        }
    }
}