package com.davidbragadeveloper.data.source

import arrow.core.Try
import com.davidbragadeveloper.domain.Album

interface AlbumRemoteDataSource {
    suspend fun discoverAlbums(apiKey: String, apiSecret: String): Try<List<Album>>
    suspend fun getAlbumById(albumId: Long): Try<Album>
}
