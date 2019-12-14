package com.davidbragadeveloper.prognet.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

typealias ProgRockAlbum = Album

data class ProgRockAlbumsResult(
    val page: Int,
    val results: List<ProgRockAlbum>,
    @SerializedName("pages") val totalPages: Int,
    @SerializedName("items") val totalResults: Long
)

@Parcelize
data class Album(
    val id: Long,
    val title: String,
    val year: String,
    @SerializedName("cover_image")val coverImage: String,
    val country: String
) : Parcelable
/*
"style": [
        "Prog Rock"
      ],
      "master_id": 835688,
      "thumb": "https://img.discogs.com/mW3IJYXF7WIHaTL2I4X7TZyYAUM=/fit-in/150x150/filters:strip_icc():format(jpeg):mode_rgb():quality(40)/discogs-images/R-7007098-1431547731-5555.jpeg.jpg",
      "format": [
        "Vinyl",
        "LP",
        "Album",
        "CD",
        "Album",
        "All Media",
        "Limited Edition"
      ],
      "country": "Sweden",
      "barcode": [
        "7 320470 199855",
        "YA3541-1 A1",
        "YA3541-1 B1",
        "ifpi L574",
        "IFPI 0723",
        "YA 3544-2 AF88370-01 manufactured by optimal media GmbH"
      ],
      "uri": "/Anekdoten-Until-All-The-Ghosts-Are-Gone/master/835688",
      "master_url": "https://api.discogs.com/masters/835688",
      "label": [
        "Virtalevy",
        "Anekdoten"
      ],
      "cover_image": "https://img.discogs.com/D0O9v-N2sOcMTT6h5tjcP6FpyX8=/fit-in/600x593/filters:strip_icc():format(jpeg):mode_rgb():quality(90)/discogs-images/R-7007098-1431547731-5555.jpeg.jpg",
      "catno": "Virta LP006",
      "community": {
        "have": 762,
        "want": 246
      },
      "year": "2015",
      "genre": [
        "Rock"
      ],
      "title": "Anekdoten - Until All The Ghosts Are Gone",
      "resource_url": "https://api.discogs.com/masters/835688",
      "type": "master",
      "id": 835688
 */
