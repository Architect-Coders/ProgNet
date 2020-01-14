package com.davidbragadeveloper.data.source

import arrow.core.Try
import com.davidbragadeveloper.domain.Album

interface AlbumRemoteDataSource {
    suspend fun discoverProgAlbums(apiKey: String, apiSecret: String): Try<List<Album>>
}
