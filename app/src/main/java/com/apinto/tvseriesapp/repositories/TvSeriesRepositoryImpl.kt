package com.apinto.tvseriesapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.apinto.tvseriesapp.BuildConfig.API_KEY
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.core.Resource.Error
import com.apinto.tvseriesapp.core.Resource.Loading
import com.apinto.tvseriesapp.core.Resource.Success
import com.apinto.tvseriesapp.model.GenreListResponse
import com.apinto.tvseriesapp.model.TvSerieEntity
import com.apinto.tvseriesapp.model.TvSeriesListResponse
import com.apinto.tvseriesapp.services.SubscriptionService
import com.apinto.tvseriesapp.services.TvSeriesService
import kotlinx.coroutines.Dispatchers.IO

class TvSeriesRepositoryImpl(private val service: TvSeriesService,
                             private val subscriptionService: SubscriptionService): TvSeriesRepository {

    override fun getTvSeriesList(page: Int): LiveData<Resource<TvSeriesListResponse>> = liveData(IO) {
        emit(Loading())

        try {
            val response = service.getTvSeries(API_KEY, page)
            emit(Success(response))
        } catch (exception: Exception) {
            emit(Error(exception))
        }

    }

    override fun getGenreList(): LiveData<Resource<GenreListResponse>> = liveData(IO) {
        emit(Loading())

        try {
            val response = service.getGenreList(API_KEY)
            emit(Success(response))
        } catch (exception: Exception) {
            emit(Error(exception))
        }
    }

    override fun getSubscriptionList(): LiveData<Resource<List<TvSerieEntity>>> = liveData(IO) {
        emit(Loading())

        try {
            emit(subscriptionService.getAllSubscriptions())
        } catch (exception: Exception) {
            emit(Error(exception))
        }
    }

}