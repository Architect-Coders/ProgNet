package com.davidbragadeveloper.prognet.data.mappers

import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.domain.ProgNetLocation
import com.davidbragadeveloper.prognet.data.local.entities.RoomAlbum
import com.davidbragadeveloper.prognet.data.local.entities.RoomTrack
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
    country = country,
    heared = heared,
    location = if(latitude != null && longitude != null) {
        ProgNetLocation(
            latitude = latitude,
            longitude = longitude
        )
    }else {
        null
    })


fun Album.toRoomAlbum() =
    RoomAlbum(
        id = id,
        title = title,
        year = year,
        coverImage = coverImage,
        country = country,
        heared = heared,
        latitude = location?.latitude,
        longitude = location?.longitude
    )


fun Album.toRoomTrackList()=
    tracks.map{
        RoomTrack(
            title = it.title,
            duration = it.duration,
            position = it.position,
            albumId = id
        )
    }


