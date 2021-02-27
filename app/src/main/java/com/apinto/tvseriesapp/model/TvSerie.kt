package com.apinto.tvseriesapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvSerie(@SerializedName("backdrop_path") val backdropPath: String,
                   @SerializedName("first_air_date") val firstAirDate: String,
                   @SerializedName("genre_ids") val genreIds: List<Long>,
                   val id: Long,
                   val name: String,
                   @SerializedName("origin_country") val originCountry: List<String>,
                   @SerializedName("original_language") val originalLanguage: String,
                   @SerializedName("original_name") val originalName: String,
                   val overview: String,
                   val popularity: Double,
                   @SerializedName("poster_path") val posterPath: String,
                   @SerializedName("vote_average") val voteAverage: Float,
                   @SerializedName("vote_count") val voteCount: Long): Parcelable