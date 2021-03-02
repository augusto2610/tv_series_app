package com.apinto.tvseriesapp.repositories

import androidx.lifecycle.LiveData
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.model.TvSerieDetails

interface TvSerieDetailsRepository {

    fun getSerieDetails(serieId: Long): LiveData<Resource<TvSerieDetails>>
}