package com.apinto.tvseriesapp.repositories

import androidx.lifecycle.LiveData
import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.model.TvSerieDetails
import com.apinto.tvseriesapp.model.TvSerieEntity

interface TvSerieDetailsRepository {

    fun getSerieDetails(serieId: Long): LiveData<Resource<TvSerieDetails>>

    suspend fun saveSubscription(tvSerieEntity: TvSerieEntity)

    suspend fun removeSubscription(tvSerieEntity: TvSerieEntity)

    suspend fun isSerieSubscribed(tvSerieEntity: TvSerieEntity): Boolean
}