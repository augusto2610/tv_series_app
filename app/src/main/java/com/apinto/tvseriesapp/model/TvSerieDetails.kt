package com.apinto.tvseriesapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class TvSerieDetails(@SerializedName("backgrop_path") val backdropPath: String,
                     val name: String,
                     @SerializedName("first_air_date") val firstAirDate: String,
                     val overview: String,
                     @SerializedName("poster_path") val posterPath: String): Parcelable {

}