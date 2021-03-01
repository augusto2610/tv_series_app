package com.apinto.tvseriesapp.repositories

import androidx.lifecycle.LiveData
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.model.ImageConfiguration

interface ImageFactoryRepository {

    fun getImageConfiguration(): LiveData<Resource<ImageConfiguration>>
}