package com.apinto.tvseriesapp.ui

import androidx.lifecycle.ViewModel
import com.apinto.tvseriesapp.repositories.TvSeriesRepository

class SplashViewModel(private val repo: TvSeriesRepository): ViewModel() {

    fun getTvSeriesList() = repo.getTvSeriesList(1)

    fun getGenreList() = repo.getGenreList()

}