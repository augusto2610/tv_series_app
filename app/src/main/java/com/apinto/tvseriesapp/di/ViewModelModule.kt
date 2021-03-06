package com.apinto.tvseriesapp.di

import com.apinto.tvseriesapp.ui.detail.DetailViewModel
import com.apinto.tvseriesapp.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {

    viewModel { HomeViewModel(get(), get()) }
    viewModel { DetailViewModel(get()) }
}