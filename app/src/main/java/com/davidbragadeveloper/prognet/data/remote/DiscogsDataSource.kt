package com.davidbragadeveloper.prognet.data.remote

import arrow.core.Try
import com.davidbragadeveloper.data.source.AlbumRemoteDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.data.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class DiscogsDataSource : AlbumRemoteDataSource {
    private val style = "Prog Rock"
    private val itemsPerPage = 20

    override suspend fun discoverProgAlbums(apiKey: String, apiSecret: String): Try<List<Album>> =
        withContext(Dispatchers.IO) {
            Try {
                DiscogsDb.service
                    .discoverProgRockAlbums(
                        apiKey = apiKey,
                        apiSecret = apiSecret,
                        style = style,
                        itemsPerPage = itemsPerPage,
                        page = Random.nextInt(500)
                    ).await()
            }
                .map { it.results }
                .map { it.toDomain() }
        }
    }
