package com.test.weatherapp.ui.fragments.base

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.test.basemodule.base.model.UiError
import com.test.basemodule.base.view.fragment.BaseFragment
import com.test.weatherapp.R

abstract class WeatherBaseFragment<ViewModel : WeatherBaseViewModel> :
    BaseFragment<ViewModel>() {
    override fun provideViewModel(viewModelClass: Class<ViewModel>): ViewModel {
        return ViewModelProvider(this).get(viewModelClass)
    }

    override fun showUiError(uiError: UiError?) {
        if (uiError == null) return
        context?.let {
            AlertDialog.Builder(it)
                .setMessage(uiError.errorText)
                .setPositiveButton(R.string.ok, null)
                .show()
        }
    }
}