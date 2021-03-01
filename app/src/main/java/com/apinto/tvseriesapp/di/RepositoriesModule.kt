package com.apinto.tvseriesapp.di

import com.apinto.tvseriesapp.repositories.ImageFactoryRepository
import com.apinto.tvseriesapp.repositories.ImageFactoryRepositoryImpl
import com.apinto.tvseriesapp.repositories.TvSeriesRepository
import com.apinto.tvseriesapp.repositories.TvSeriesRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single<TvSeriesRepository> { TvSeriesRepositoryImpl(get()) }

    single<ImageFactoryRepository> { ImageFactoryRepositoryImpl(get()) }
}