package com.apinto.tvseriesapp.ui.detail

import androidx.lifecycle.ViewModel
import com.apinto.tvseriesapp.repositories.TvSerieDetailsRepository

class DetailViewModel(private val repo: TvSerieDetailsRepository): ViewModel() {

    fun getDetails(serieId: Long) = repo.getSerieDetails(serieId)

}