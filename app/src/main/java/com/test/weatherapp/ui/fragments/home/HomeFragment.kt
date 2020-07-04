package com.test.weatherapp.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.basemodule.base.view.adapter.OnItemClickListener
import com.test.basemodule.utils.initLinear
import com.test.weatherapp.R
import com.test.weatherapp.ui.fragments.base.WeatherBaseFragment
import com.test.weatherapp.ui.fragments.home.adapter.CitiesAdapter
import com.test.weatherapp.vo.CityForecast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*

@AndroidEntryPoint
class HomeFragment : WeatherBaseFragment<HomeViewModel>() {
    lateinit var adapter: CitiesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initToolbar()
    }

    private fun initToolbar() {
        fHomeToolbar.setOnMenuItemClickListener { onMenuItemClicked(it) }
    }

    private fun onMenuItemClicked(it: MenuItem): Boolean {
        return when(it.itemId){
            R.id.app_bar_search -> {
                findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
                true
            }
            else -> false
        }
    }

    private fun initRecyclerView() {
        adapter = CitiesAdapter(object : OnItemClickListener<CityForecast> {
            override fun onItemClick(view: View?, item: CityForecast?) {
                onCityItemClicked(view, item)
            }
        })
        fHomeRvBookmarkedCities.initLinear(requireContext(), adapter)
    }

    private fun onCityItemClicked(view: View?, item: CityForecast?) {
        when(view?.id) {
            R.id.iCityIvBookmark -> viewModel.toggleBookmark(item)
        }
    }
}