package com.test.weatherapp.ui.fragments.home

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.test.basemodule.base.view.adapter.OnItemClickListener
import com.test.basemodule.utils.initLinear
import com.test.weatherapp.R
import com.test.weatherapp.ui.fragments.base.WeatherBaseFragment
import com.test.weatherapp.ui.fragments.forecastdetails.ForecastDetailsFragment
import com.test.weatherapp.ui.fragments.home.adapter.CitiesAdapter
import com.test.weatherapp.util.LocationHelper
import com.test.weatherapp.vo.CityForecast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : WeatherBaseFragment<HomeViewModel>() {
    lateinit var adapter: CitiesAdapter
    var locationHelper: LocationHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onResume() {
        super.onResume()

        if (viewModel.shouldListenToLocation())
            takeLocationPermission()
    }

    override fun onPause() {
        super.onPause()
        locationHelper?.stopListening()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        initToolbar()
        initObservers()
    }

    private fun initObservers() {
        viewModel.ldBookmarkedCities.observe(viewLifecycleOwner, Observer { adapter.setData(it) })
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
            override fun onItemClick(view: View?, item: CityForecast?, position: Int) {
                onCityItemClicked(view, item)
            }
        })
        fHomeRvBookmarkedCities.initLinear(requireContext(), adapter)
    }

    private fun onCityItemClicked(view: View?, item: CityForecast?) {
        when(view?.id) {
            R.id.iCityIvBookmark -> viewModel.toggleBookmark(item)
            else ->
                findNavController().navigate(R.id.action_homeFragment_to_forecastDetailsFragment,
                        bundleOf( ForecastDetailsFragment.KEY_CITY_ID to item!!.id ))
        }
    }

    private fun takeLocationPermission() {
        Dexter.withContext(requireContext())
            .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    onLocationPermissionGranted()
                    locationHelper?.startListening()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    viewModel.addDefaultCity()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    viewModel.addDefaultCity()
                }

            }).check()
    }

    private fun onLocationPermissionGranted() {
        locationHelper = LocationHelper(activity, object : LocationCallback(){
            override fun onLocationResult(result: LocationResult?) {
                Timber.d("$result.")
                if (result == null || result.lastLocation == null) return

                viewModel.addCurrentCityToBookmarks(result.lastLocation)
                locationHelper?.stopListening()
            }

            override fun onLocationAvailability(p0: LocationAvailability?) {
                Timber.d("$p0.")
            }
        })
    }
}