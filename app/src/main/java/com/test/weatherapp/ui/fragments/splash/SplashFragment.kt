package com.test.weatherapp.ui.fragments.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.test.basemodule.base.model.VMNotification
import com.test.basemodule.base.view.widgets.LoadingErrorLayout
import com.test.weatherapp.R
import com.test.weatherapp.ui.fragments.base.WeatherBaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : WeatherBaseFragment<SplashViewModel>() {
    private lateinit var loadingErrorLayout: LoadingErrorLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadingErrorLayout = LoadingErrorLayout(
            requireContext(), R.layout.fragment_splash
            , R.layout.layout_loading, R.layout.layout_loading)
        return loadingErrorLayout
    }

    override fun doAction(vmNotification: VMNotification) {
        super.doAction(vmNotification)
        if (ACTION_TO_HOME == vmNotification.getAction())
            findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
    }

    override fun showLoading(loadingMode: Int) {
        super.showLoading(loadingMode)
        loadingErrorLayout.viewStat = LoadingErrorLayout.STAT_LOADING_OVERLAY
    }

    override fun hideLoading(loadingMode: Int) {
        super.hideLoading(loadingMode)
        loadingErrorLayout.viewStat = LoadingErrorLayout.STAT_DEFAULT
    }

    companion object {
        const val ACTION_TO_HOME = "ACTION_TO_HOME"
        const val LOADING_OVERLAY = 1
    }
}