package com.apinto.tvseriesapp.repositories

import androidx.lifecycle.LiveData
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.model.TvSeriesListResponse

interface TvSeriesRepository {

    fun getTvSeriesList(apiKey: String, page: Int): LiveData<Resource<TvSeriesListResponse>>

}