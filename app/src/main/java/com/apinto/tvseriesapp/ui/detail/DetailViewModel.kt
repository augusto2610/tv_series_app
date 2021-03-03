package com.apinto.tvseriesapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apinto.tvseriesapp.model.TvSerieEntity
import com.apinto.tvseriesapp.repositories.TvSerieDetailsRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: TvSerieDetailsRepository): ViewModel() {

    fun getDetails(serieId: Long) = repo.getSerieDetails(serieId)

    fun saveOrDeleteSubscription(tvSerieEntity: TvSerieEntity) {
        viewModelScope.launch {
            if (repo.isSerieSubscribed(tvSerieEntity)) {
                repo.removeSubscription(tvSerieEntity)
            } else {
                repo.saveSubscription(tvSerieEntity)
            }
        }
    }

    suspend fun isSerieSubscribed(tvSerieEntity: TvSerieEntity) = repo.isSerieSubscribed(tvSerieEntity)

}