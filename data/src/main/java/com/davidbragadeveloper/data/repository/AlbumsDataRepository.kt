package com.davidbragadeveloper.data.repository

import arrow.core.Try
import com.davidbragadeveloper.data.source.AlbumRemoteDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository

class AlbumsDataRepository(
    private val dataSource: AlbumRemoteDataSource,
    private val apiKey: String,
    private val apiSecret: String
) : AlbumsRepository {
    override suspend fun dicoverProgAlbums(): Try<List<Album>> =
        dataSource.discoverProgAlbums(apiKey = apiKey, apiSecret = apiSecret)
}
