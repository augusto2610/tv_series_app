package com.apinto.tvseriesapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreListResponse(val genres: List<Genre>): Parcelable