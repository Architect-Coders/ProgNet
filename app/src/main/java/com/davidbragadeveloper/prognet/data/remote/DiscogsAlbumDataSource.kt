package com.davidbragadeveloper.prognet.data.remote

import arrow.core.Try
import com.davidbragadeveloper.data.source.AlbumRemoteDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.data.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

private const val ITEMS_PER_PAGE = 20
class DiscogsAlbumDataSource(private val discogsDb: DiscogsDb) : AlbumRemoteDataSource {

    private val style = "Prog Rock"

    override suspend fun discoverProgAlbums(apiKey: String, apiSecret: String): Try<List<Album>> =
        withContext(Dispatchers.IO) {
            Try {
                discogsDb.service
                    .discoverProgRockAlbums(
                        apiKey = apiKey,
                        apiSecret = apiSecret,
                        style = style,
                        itemsPerPage = ITEMS_PER_PAGE,
                        page = Random.nextInt(500)
                    ).await()
            }
                .map { it.results }
                .map { it.map{it.toDomain()} }
        }
    }
