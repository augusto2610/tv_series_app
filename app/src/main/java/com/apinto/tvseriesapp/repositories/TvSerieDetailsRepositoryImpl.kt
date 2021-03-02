package com.apinto.tvseriesapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.apinto.tvseriesapp.BuildConfig.API_KEY
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.core.Resource.Error
import com.apinto.tvseriesapp.core.Resource.Loading
import com.apinto.tvseriesapp.model.TvSerieDetails
import com.apinto.tvseriesapp.services.TvSeriesService
import kotlinx.coroutines.Dispatchers.IO
import java.lang.Exception

class TvSerieDetailsRepositoryImpl(private val service: TvSeriesService): TvSerieDetailsRepository {

    override fun getSerieDetails(serieId: Long): LiveData<Resource<TvSerieDetails>> = liveData(IO) {

        emit(Loading())

        try {
            emit(Resource.Success(service.getSerieDetail(serieId, API_KEY)))
        } catch (exception: Exception) {
            emit(Error(exception))
        }

    }

}