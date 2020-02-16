package com.davidbragadeveloper.usecases.repositories

import arrow.core.Try
import com.davidbragadeveloper.domain.Album

interface AlbumsRepository {
    suspend fun dicoverAlbums(): Try<List<Album>>
    suspend fun getAlbumById(albumId: Long): Try<Album>
    suspend fun toggleAlbumHeared(album: Album): Try<Boolean>
}
