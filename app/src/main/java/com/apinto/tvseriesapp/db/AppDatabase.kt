package com.apinto.tvseriesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.apinto.tvseriesapp.model.TvSerieEntity

@Database(entities = [TvSerieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun tvSerieDao(): TvSerieDao
}