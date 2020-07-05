package com.test.weatherapp.ui.fragments.forecastdetails

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.test.basemodule.utils.initLinear
import com.test.weatherapp.R
import com.test.weatherapp.databinding.FragmentForecastDetailsBinding
import com.test.weatherapp.ui.fragments.base.BindingBaseFragment
import com.test.weatherapp.ui.fragments.forecastdetails.adapter.DayForecastAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_forecast_details.*
import kotlinx.android.synthetic.main.layout_error.*

@AndroidEntryPoint
class ForecastDetailsFragment :
    BindingBaseFragment<ForecastDetailsViewModel, FragmentForecastDetailsBinding>() {

    lateinit var adapter: DayForecastAdapter
    override val layoutRes: Int
        get() = R.layout.fragment_forecast_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.extractArgument(arguments)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecycler()
        initObservers()
        initListeners()
    }

    private fun initListeners() {
        lErrorBtnRetry.setOnClickListener { viewModel.loadForecast() }
    }

    private fun initRecycler() {
        adapter = DayForecastAdapter()
        fFDetailsRvDays.initLinear(requireContext(), adapter)
    }

    private fun initObservers() {
        binding.city = viewModel.ldCity
        binding.resource = viewModel.ldDaysForecast

        viewModel.ldDaysForecast.observe(viewLifecycleOwner, Observer {
            if (it.data != null)
                adapter.setData(it.data!!)
        })

        viewModel.ldIsBookmarked.observe(viewLifecycleOwner, Observer {
            fFDetailsToolbar.menu.findItem(R.id.app_bar_bookmark)
                .setIcon(if (it) R.drawable.ic_bookmarked else R.drawable.ic_bookmark_border)
        })
    }

    private fun initToolbar() {
        fFDetailsToolbar.setNavigationOnClickListener { requireActivity().onBackPressed() }
        fFDetailsToolbar.setOnMenuItemClickListener { onMenuItemClicked(it) }
    }

    private fun onMenuItemClicked(it: MenuItem): Boolean {
        return when(it.itemId){
            R.id.app_bar_bookmark -> {
                viewModel.toggleBookmark()
                true
            }
            else -> false
        }
    }

    companion object {
        const val KEY_CITY_ID = "KEY_CITY_ID"
    }

}