package com.apinto.tvseriesapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class TvSeriesListResponse(val page: Int,
                           @SerializedName("total_pages") val totalPages: Int,
                           @SerializedName("total_results") val totalResults: Long,
                           val results: List<TvSerie>): Parcelable