package com.apinto.tvseriesapp.repositories

import androidx.lifecycle.LiveData
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.model.GenreListResponse
import com.apinto.tvseriesapp.model.TvSerieEntity
import com.apinto.tvseriesapp.model.TvSeriesListResponse

interface TvSeriesRepository {

    fun getTvSeriesList(page: Int): LiveData<Resource<TvSeriesListResponse>>

    fun getGenreList(): LiveData<Resource<GenreListResponse>>

    fun getSubscriptionList(): LiveData<Resource<List<TvSerieEntity>>>

}