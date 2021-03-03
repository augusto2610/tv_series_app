package com.apinto.tvseriesapp

import android.app.Application
import com.apinto.tvseriesapp.BuildConfig.DEBUG
import com.apinto.tvseriesapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class TvSeriesApp: Application() {

    override fun onCreate() {
        super.onCreate()

        //Log only when is debug mode
        if (DEBUG) Timber.plant(Timber.DebugTree())

        //Init koin library
        startKoin {
            androidContext(this@TvSeriesApp)
            modules(servicesModule, repositoriesModule, viewModelsModule, helpersModule, databaseModule)
        }
    }
}