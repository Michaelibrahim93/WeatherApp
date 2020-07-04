package com.test.weatherapp.ui.fragments.home.adapter

import com.test.basemodule.base.view.adapter.BaseViewHolder
import com.test.weatherapp.databinding.ItemCityBinding
import com.test.weatherapp.vo.CityForecast

class CityViewHolder(binding: ItemCityBinding) :
    BaseViewHolder<CityForecast, ItemCityBinding>(binding),
    CityItemCallbacks {
    init {
        binding.callbacks = this
    }
    override fun onBindView(itemData: CityForecast?) {
        binding.cityForecast = itemData
    }

    override fun onBookmarkClicked() {
        onItemClickListener?.onItemClick(binding.iCityIvBookmark, itemData, adapterPosition)
    }
}