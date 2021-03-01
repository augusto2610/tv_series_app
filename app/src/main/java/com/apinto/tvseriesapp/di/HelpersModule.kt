package com.apinto.tvseriesapp.di

import com.apinto.tvseriesapp.core.ImageFactoryHelper
import org.koin.dsl.module

val helpersModule = module {
    single { ImageFactoryHelper() }
}