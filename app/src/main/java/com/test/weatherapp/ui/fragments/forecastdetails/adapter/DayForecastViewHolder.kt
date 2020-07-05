package com.test.weatherapp.ui.fragments.forecastdetails.adapter

import com.test.basemodule.base.view.adapter.BaseViewHolder
import com.test.weatherapp.databinding.ItemDayForecastBinding
import com.test.weatherapp.vo.WeatherForecast
import java.text.SimpleDateFormat

class DayForecastViewHolder(
    binding: ItemDayForecastBinding,
    private val dateFormat: SimpleDateFormat
) : BaseViewHolder<WeatherForecast, ItemDayForecastBinding>(
    binding
) {

    init {
        binding.dateFormat = this.dateFormat
    }

    override fun onBindView(itemData: WeatherForecast?) {
        binding.forecast = itemData
    }
}