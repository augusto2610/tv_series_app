package com.apinto.tvseriesapp.di

import androidx.room.Room
import com.apinto.tvseriesapp.db.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidApplication(), AppDatabase::class.java, "subscriptionsDb").build()
    }
    single { get<AppDatabase>().tvSerieDao() }
}