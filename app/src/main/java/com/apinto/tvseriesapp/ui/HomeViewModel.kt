package com.apinto.tvseriesapp.ui

import androidx.lifecycle.ViewModel
import com.apinto.tvseriesapp.repositories.ImageFactoryRepository
import com.apinto.tvseriesapp.repositories.TvSeriesRepository

class HomeViewModel(private val repo: TvSeriesRepository, private val imageFactory: ImageFactoryRepository): ViewModel() {

    fun getTvSeriesList() = repo.getTvSeriesList(1)

    fun getGenreList() = repo.getGenreList()

    fun getConfiguration() = imageFactory.getImageConfiguration()

}