package com.test.basemodule.base.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.test.basemodule.base.model.LoadingState
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.model.VMNotification
import com.test.basemodule.base.view.LoadingViewOwner
import com.test.basemodule.base.viewmodel.BaseViewModel
import com.test.basemodule.utils.GenericsUtils

/**
 * Created by Michael on 12/27/17.
 */

abstract class BaseFragment<ViewModel : BaseViewModel> : Fragment(), LoadingViewOwner {
    protected lateinit var viewModel: ViewModel
    private var loadingModes: Set<Int>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelClass: Class<ViewModel> = GenericsUtils.getViewModelClass(javaClass)
        viewModel = provideViewModel(viewModelClass)
    }

    inline fun<reified FragmentContainer> findFragmentContainer(): FragmentContainer? {
        var fragment = parentFragment
        while (fragment != null) {
            if (fragment is FragmentContainer)
                return fragment
            fragment = fragment.parentFragment
        }

        return if (activity is FragmentContainer) activity as FragmentContainer
                else null
    }

    //this function should be implemented in project base fragment.
    abstract fun provideViewModel(viewModelClass: Class<ViewModel>): ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.ldLoadingState.observe(this.viewLifecycleOwner, Observer { onLoadingStateUpdated(it) })
        viewModel.ldActions.observe(this.viewLifecycleOwner, Observer { onActionsUpdated(it) })
        viewModel.ldUiError.observe(this.viewLifecycleOwner, Observer { onReceiveError(it) })
    }

    private fun onReceiveError(uiError: UiError?) {
        showUiError(uiError)
        viewModel.clearUiError()
    }

    private fun onActionsUpdated(vmNotifications: MutableSet<VMNotification>) {
        for (itr in vmNotifications) doAction(itr)
        viewModel.clearActions()
    }

    private fun onLoadingStateUpdated(loadingState: LoadingState) {
        val newLoadingModes: Set<Int> = loadingState.getLoadingModes()
        for (loadingMode in newLoadingModes) showLoading(loadingMode)

        if (loadingModes != null) {

            for (loadingMode in loadingModes!!) if (!newLoadingModes.contains(loadingMode)) hideLoading(
                loadingMode
            )
        }

        loadingModes = newLoadingModes
    }

    override fun showLoading(loadingMode: Int) {

    }

    override fun hideLoading(loadingMode: Int) {

    }

    protected open fun showUiError(uiError: UiError?) {

    }

    protected open fun doAction(vmNotification: VMNotification) {

    }
}