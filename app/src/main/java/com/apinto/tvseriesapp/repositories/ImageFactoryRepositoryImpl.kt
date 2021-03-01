package com.apinto.tvseriesapp.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.apinto.tvseriesapp.BuildConfig.API_KEY
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.core.Resource.Error
import com.apinto.tvseriesapp.core.Resource.Loading
import com.apinto.tvseriesapp.core.Resource.Success
import com.apinto.tvseriesapp.model.ImageConfiguration
import com.apinto.tvseriesapp.services.TvSeriesService
import kotlinx.coroutines.Dispatchers.IO

class ImageFactoryRepositoryImpl(private val service: TvSeriesService): ImageFactoryRepository {

    override fun getImageConfiguration(): LiveData<Resource<ImageConfiguration>> = liveData(IO) {
        emit(Loading())

        try {
            emit(Success(service.getConfiguration(API_KEY).images))
        } catch (exception: Exception) {
            emit(Error(exception))
        }
    }
}