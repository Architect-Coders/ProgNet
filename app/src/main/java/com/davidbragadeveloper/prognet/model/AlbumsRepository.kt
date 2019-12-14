package com.davidbragadeveloper.prognet.model

import arrow.core.Try
import com.davidbragadeveloper.prognet.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

class AlbumsRepository {
    private val apiKey = BuildConfig.discogsApiKey
    private val apiSecret = BuildConfig.discogsApiSecret
    private val style = "Prog Rock"
    private val itemsPerPage = 20

    suspend fun discoverProgAlbums(): Try<List<ProgRockAlbum>> =
        withContext(Dispatchers.IO) {
            Try {
                DiscogsDb
                    .service
                    .discoverProgRockAlbums(
                        apiKey = apiKey,
                        apiSecret = apiSecret,
                        style = style,
                        itemsPerPage = itemsPerPage,
                        page = Random.nextInt(500)
                    ).await().results
            }
        }
    }
