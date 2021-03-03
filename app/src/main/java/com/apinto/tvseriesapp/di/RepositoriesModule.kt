package com.apinto.tvseriesapp.di

import com.apinto.tvseriesapp.repositories.*
import org.koin.dsl.module

val repositoriesModule = module {
    single<TvSeriesRepository> { TvSeriesRepositoryImpl(get(), get()) }
    single<ImageFactoryRepository> { ImageFactoryRepositoryImpl(get()) }
    single<TvSerieDetailsRepository> { TvSerieDetailsRepositoryImpl(get(), get()) }
}