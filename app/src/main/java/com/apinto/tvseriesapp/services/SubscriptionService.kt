package com.apinto.tvseriesapp.services

import com.apinto.tvseriesapp.core.Resource
import com.apinto.tvseriesapp.db.TvSerieDao
import com.apinto.tvseriesapp.model.TvSerieEntity

class SubscriptionService(private val tvSerieDao: TvSerieDao) {

    suspend fun saveSubscription(tvSerie: TvSerieEntity) {
        tvSerieDao.addSubscription(tvSerie)
    }

    suspend fun removeSubscription(tvSerie: TvSerieEntity) {
        tvSerieDao.removeSubscription(tvSerie)
    }

    suspend fun getAllSubscriptions(): Resource<List<TvSerieEntity>> {
        return Resource.Success(tvSerieDao.getAllSubscriptions())
    }

    suspend fun isSerieSubscribed(serieId: Long): Boolean {
        return tvSerieDao.getSerieTvById(serieId) != null
    }

}