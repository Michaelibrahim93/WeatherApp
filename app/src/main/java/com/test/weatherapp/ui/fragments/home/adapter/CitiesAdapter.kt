package com.test.weatherapp.ui.fragments.home.adapter

import android.view.ViewGroup
import com.test.basemodule.base.view.adapter.AbstractRecyclerAdapter
import com.test.basemodule.base.view.adapter.OnItemClickListener
import com.test.weatherapp.R
import com.test.weatherapp.databinding.ItemCityBinding
import com.test.weatherapp.vo.CityForecast

class CitiesAdapter(
    clickListener: OnItemClickListener<CityForecast>? = null
) : AbstractRecyclerAdapter<CityForecast, ItemCityBinding, CityViewHolder>(
    clickListener
) {
    override val viewRes: Int
        get() = R.layout.item_city

    override fun onCreateDefaultViewHolder(
        viewGroup: ViewGroup?,
        viewType: Int,
        binding: ItemCityBinding
    ): CityViewHolder {
        return CityViewHolder(binding)
    }

    override fun onViewHolderBond(k: CityViewHolder, position: Int) {}
}