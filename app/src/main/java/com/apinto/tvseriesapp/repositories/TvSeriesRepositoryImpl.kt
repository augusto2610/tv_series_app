package com.apinto.tvseriesapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.apinto.tvseriesapp.BuildConfig
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.model.TvSeriesListResponse
import com.apinto.tvseriesapp.services.TvSeriesService
import kotlinx.coroutines.Dispatchers.IO

class TvSeriesRepositoryImpl(private val service: TvSeriesService): TvSeriesRepository {

    override fun getTvSeriesList(apiKey: String, page: Int): LiveData<Resource<TvSeriesListResponse>> = liveData(IO){
        emit(Resource.Loading())

        try {
            val response = service.getTvSeries(BuildConfig.API_KEY, 1)
            emit(Resource.Success(response))
        } catch (exception: Exception) {
            emit(Resource.Error(exception))
        }

    }

}