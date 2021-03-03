package com.apinto.tvseriesapp.db

import androidx.room.*
import com.apinto.tvseriesapp.model.TvSerieEntity


@Dao
interface TvSerieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addSubscription(tvSerie: TvSerieEntity)

    @Delete
    suspend fun removeSubscription(tvSerie: TvSerieEntity)

    @Query("SELECT * FROM serieSubscription")
    suspend fun getAllSubscriptions(): List<TvSerieEntity>

    @Query("SELECT * FROM serieSubscription WHERE serieId = :serieId")
    suspend fun getSerieTvById(serieId: Long): TvSerieEntity?

}