package com.davidbragadeveloper.prognet.data

import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.data.remote.DiscogsAlbum

fun DiscogsAlbum.toDomain() = Album(
    id = id,
    title = title,
    year = year,
    coverImage = coverImage,
    country = country
)

fun List<DiscogsAlbum>.toDomain() = this.map { it.toDomain() }
