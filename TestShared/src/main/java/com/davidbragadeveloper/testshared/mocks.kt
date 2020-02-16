package com.davidbragadeveloper.testshared

import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.domain.ProgNetLocation
import com.davidbragadeveloper.domain.Track


val mockedSimpleAlbum = Album(
    id = 0,
    title = "title",
    year = "1985",
    coverImage = "coverImage",
    country = "Spain"
)


val mockedAlbum = Album(
    id = 0,
    title = "title",
    year = "1985",
    coverImage = "coverImage",
    country = "Spain",
    tracks = (0..10).map{
        Track(
            title = "title $it",
            duration = "duration $it",
            position = "position $it"
        )
    },
    heared = true,
    location = ProgNetLocation(
        latitude = 0.235,
        longitude = 7.529
    )
)