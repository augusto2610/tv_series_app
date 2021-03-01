package com.apinto.tvseriesapp.services

import com.apinto.tvseriesapp.model.GenreListResponse
import com.apinto.tvseriesapp.model.ImageConfigurationResponse
import com.apinto.tvseriesapp.model.TvSeriesListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TvSeriesService {

    @GET("/3/tv/popular?")
    suspend fun getTvSeries(@Query("api_key") apiKey: String,
                            @Query("page") page: Int): TvSeriesListResponse

    @GET("3/genre/tv/list?")
    suspend fun getGenreList(@Query("api_key") apiKey: String): GenreListResponse

    @GET("3/configuration?")
    suspend fun getConfiguration(@Query("api_key") apiKey: String): ImageConfigurationResponse
}