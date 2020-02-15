package com.davidbragadeveloper.prognet.data.mappers

import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.data.local.entities.RoomAlbum
import com.davidbragadeveloper.prognet.data.remote.DiscogsAlbum

fun DiscogsAlbum.toDomain() = Album(
    id = id,
    title = title?: "",
    year = year ?: "",
    coverImage = coverImage?: "",
    country = country?: "",
    tracks = discogsTracks?.map { it.toDomain() } ?: listOf()
)



fun RoomAlbum.toDomain() = Album(
    id = id,
    title = title,
    year = year,
    coverImage = coverImage,
    country = country
)


fun Album.toRoomAlbum() =
    RoomAlbum(
        id = id,
        title = title,
        year = year,
        coverImage = coverImage,
        country = country
    )

