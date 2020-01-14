package com.davidbragadeveloper.prognet.data.remote

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class DiscogsAlbumsResult(
    val page: Int,
    val results: List<DiscogsAlbum>,
    @SerializedName("pages") val totalPages: Int,
    @SerializedName("items") val totalResults: Long
)

@Parcelize
data class DiscogsAlbum(
    val id: Long,
    val title: String,
    val year: String,
    @SerializedName("cover_image")val coverImage: String,
    val country: String
) : Parcelable
