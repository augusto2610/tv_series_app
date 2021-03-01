package com.apinto.tvseriesapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageConfiguration(@SerializedName("base_url") val baseUrl: String,
@SerializedName("secure_base_url") val secureBaseUrl: String,
@SerializedName("backdrop_sizes") val backdropSizes: List<String>,
@SerializedName("poster_sizes") val posterSizes: List<String>): Parcelable