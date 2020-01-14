package com.davidbragadeveloper.usecases.repositories

import arrow.core.Try
import com.davidbragadeveloper.domain.Album

interface AlbumsRepository {
    suspend fun dicoverProgAlbums(): Try<List<Album>>
}
