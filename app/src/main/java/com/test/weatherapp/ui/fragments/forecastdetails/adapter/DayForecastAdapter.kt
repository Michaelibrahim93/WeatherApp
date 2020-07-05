package com.test.weatherapp.ui.fragments.forecastdetails.adapter

import android.view.ViewGroup
import com.test.basemodule.base.view.adapter.AbstractRecyclerAdapter
import com.test.weatherapp.R
import com.test.weatherapp.databinding.ItemDayForecastBinding
import com.test.weatherapp.vo.WeatherForecast
import java.text.SimpleDateFormat
import java.util.*

class DayForecastAdapter :
    AbstractRecyclerAdapter<WeatherForecast, ItemDayForecastBinding, DayForecastViewHolder>() {
    val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
    override val viewRes: Int
        get() = R.layout.item_day_forecast

    override fun onCreateDefaultViewHolder(
        viewGroup: ViewGroup?,
        viewType: Int,
        binding: ItemDayForecastBinding
    ): DayForecastViewHolder {
        return DayForecastViewHolder(binding, simpleDateFormat)
    }

    override fun onViewHolderBond(k: DayForecastViewHolder, position: Int) {

    }
}