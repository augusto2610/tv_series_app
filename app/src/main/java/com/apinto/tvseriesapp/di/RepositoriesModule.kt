package com.apinto.tvseriesapp.di

import com.apinto.tvseriesapp.repositories.TvSeriesRepository
import com.apinto.tvseriesapp.repositories.TvSeriesRepositoryImpl
import org.koin.dsl.module

val repositoriesModule = module {
    single<TvSeriesRepository> {TvSeriesRepositoryImpl(get())}
}