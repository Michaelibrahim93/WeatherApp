package com.test.weatherapp.ui.fragments.search

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.test.weatherapp.dataaccess.repository.CityRepository
import com.test.weatherapp.ui.fragments.base.WeatherBaseViewModel
import com.test.weatherapp.dataaccess.paging.CityPagingSource
import com.test.weatherapp.usecases.ToggleBookmarkUseCase
import com.test.weatherapp.util.DebounceOperator
import com.test.weatherapp.vo.CityForecast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@ExperimentalCoroutinesApi
class SearchViewModel @ViewModelInject constructor(
    application: Application,
    private val cityRepository: CityRepository,
    private val toggleBookmarkUseCase: ToggleBookmarkUseCase
) : WeatherBaseViewModel(application) {

    private val debounceOperator = DebounceOperator<String>()
    val ldSearchString = MutableLiveData<String>()

    val ldFlow = MutableLiveData<Flow<PagingData<CityForecast>>>()

    init {
        ldSearchString.observeForever { updateSearchString(it) }
        updateFlow("")
    }

    private fun updateSearchString(string: String) {
        debounceOperator.debounce(string, this::fetchSearchResults)
    }
    private fun fetchSearchResults(searchString: String) = launchDataLoad {
        Timber.d("fetchSearchResults $searchString")
        updateFlow(searchString)
    }

    private fun updateFlow(searchString: String) {
        viewModelScope.coroutineContext.cancelChildren()
        ldFlow.value = Pager(PagingConfig(pageSize = 50)) {
            CityPagingSource(searchString, cityRepository)
        }.flow.cachedIn(viewModelScope)
    }

    fun toggleBookmark(item: CityForecast?, position: Int) = launchDataLoad {
        item?.let {
            toggleBookmarkUseCase.toggleBookmark(it)
            it.isBookMarked = !it.isBookMarked
            addAction(SearchFragment.ACTION_REFRESH_LIST, position)
        }
    }
}