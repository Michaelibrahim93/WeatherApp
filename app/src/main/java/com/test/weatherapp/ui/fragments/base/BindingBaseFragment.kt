package com.test.weatherapp.ui.fragments.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BindingBaseFragment<ViewModel : WeatherBaseViewModel, Binding : ViewDataBinding>
    : WeatherBaseFragment<ViewModel>() {

    protected lateinit var binding: Binding
    protected abstract val layoutRes: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflateBinding(inflater, container).root
    }

    private fun inflateBinding(layoutInflater: LayoutInflater, container: ViewGroup?): Binding {
        binding = DataBindingUtil.inflate(layoutInflater, layoutRes, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding
    }
}