package com.test.weatherapp.ui.fragments.search

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import com.test.basemodule.base.model.VMNotification
import com.test.basemodule.base.view.adapter.OnItemClickListener
import com.test.basemodule.utils.initLinear
import com.test.weatherapp.R
import com.test.weatherapp.databinding.FragmentSearchBinding
import com.test.weatherapp.ui.fragments.base.BindingBaseFragment
import com.test.weatherapp.ui.fragments.forecastdetails.ForecastDetailsFragment
import com.test.weatherapp.ui.fragments.search.adapter.CitySearchAdapter
import com.test.weatherapp.vo.CityForecast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : BindingBaseFragment<SearchViewModel, FragmentSearchBinding>() {
    override val layoutRes: Int
        get() = R.layout.fragment_search

    lateinit var adapter: CitySearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        initRecycler()
        initObservers()
        fSearchEditText.requestFocus()
    }

    private fun initObservers() {
        viewModel.ldFlow.observe(viewLifecycleOwner, Observer { onFlowUpdated(it) })
    }

    private fun onFlowUpdated(it: Flow<PagingData<CityForecast>>) {
        lifecycleScope.launch {
            it.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initBinding() {
        binding.searchString = viewModel.ldSearchString
    }

    private fun initRecycler() {
        adapter = CitySearchAdapter(object : OnItemClickListener<CityForecast> {
            override fun onItemClick(view: View?, item: CityForecast?, position: Int) {
                onCityItemClicked(view, item, position)
            }
        })
        fSearchRvBookmarkedCities.initLinear(requireContext(), adapter)
    }

    private fun onCityItemClicked(view: View?, item: CityForecast?, position: Int) {
        when(view?.id) {
            R.id.iCityIvBookmark -> viewModel.toggleBookmark(item, position)

            else -> findNavController().navigate(
                    R.id.action_searchFragment_to_forecastDetailsFragment,
                    bundleOf( ForecastDetailsFragment.KEY_CITY_ID to item!!.id )
                )
        }
    }

    override fun doAction(vmNotification: VMNotification) {
        super.doAction(vmNotification)
        when(vmNotification.getAction()){
            ACTION_REFRESH_LIST -> adapter.notifyItemChanged(vmNotification.getTag() as Int)
        }
    }

    companion object {
        const val ACTION_REFRESH_LIST = "ACTION_REFRESH_LIST"
    }
}