package com.test.weatherapp.dataaccess.paging

import androidx.paging.*
import com.test.basemodule.utils.containsItem
import com.test.weatherapp.dataaccess.repository.CityRepository
import com.test.weatherapp.vo.City
import com.test.weatherapp.vo.CityForecast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber

@ExperimentalCoroutinesApi
class CityPagingSource(
    private val query: String,
    private val cityRepo: CityRepository
) : PagingSource<Int, CityForecast>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CityForecast> {
        val pageNumber = params.key?:0
        val offset = params.loadSize * pageNumber

        val searchResults = search(params, offset)

        return LoadResult.Page(
            data = searchResults,
            prevKey = if (pageNumber > 0) pageNumber-1 else null,
            nextKey = pageNumber+1
        )
    }

    private suspend fun search(params: LoadParams<Int>, offset: Int): List<CityForecast> {
        var bookmarkedCities: List<CityForecast> = ArrayList()
        try {
            bookmarkedCities = cityRepo.loadBookmarkedCitiesSync()
            Timber.d("${bookmarkedCities.size}")
        }catch (t: Throwable) {
            Timber.w(t)
        }

        val searchChunks = query.split("\\s*,\\s*")
        val cityNameQuery = if (searchChunks.isNotEmpty()) searchChunks[0] else ""

        var searchResults = cityRepo.search(cityNameQuery, params.loadSize, offset)
        searchResults = applyPostQueryFilter(searchResults, searchChunks)

        return searchResults.map { CityForecast.create(it, bookmarkedCities.containsItem(it)) }
    }

    private fun applyPostQueryFilter(searchResults: List<City>, searchChunks: List<String>): List<City> {
        return when (searchChunks.size) {
            2 -> {
                searchResults.filter {
                    it.state?.contains(searchChunks[1], true)?:false
                            || it.country.contains(searchChunks[1], true)
                }
            }
            3 -> {
                searchResults
                    .filter { it.state?.contains(searchChunks[1], true)?:false }
                    .filter { it.country.contains(searchChunks[2], true) }
            }
            else -> searchResults
        }
    }
}