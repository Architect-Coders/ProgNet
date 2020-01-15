package com.davidbragadeveloper.data.repository

import arrow.core.Try
import arrow.core.getOrDefault
import com.davidbragadeveloper.data.source.AlbumLocalDataSource
import com.davidbragadeveloper.data.source.AlbumRemoteDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.usecases.repositories.AlbumsRepository

class AlbumsDataRepository(
    private val remoteDataSource: AlbumRemoteDataSource,
    private val localDataSource: AlbumLocalDataSource,
    private val apiKey: String,
    private val apiSecret: String
) : AlbumsRepository {

    override suspend fun dicoverProgAlbums(): Try<List<Album>> =
        remoteDataSource
            .discoverProgAlbums(apiKey = apiKey, apiSecret = apiSecret)
            .also {localDataSource.saveAlbums(it.getOrDefault { listOf() }) }

}
