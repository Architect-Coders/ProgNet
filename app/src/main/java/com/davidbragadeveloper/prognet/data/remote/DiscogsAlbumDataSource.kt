package com.davidbragadeveloper.prognet.data.remote

import arrow.core.Try
import com.davidbragadeveloper.data.source.AlbumRemoteDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.data.mappers.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.random.Random

private const val ITEMS_PER_PAGE = 20
class DiscogsAlbumDataSource(private val discogsDb: DiscogsDb) : AlbumRemoteDataSource {

    companion object{
        private const val style = "Prog Rock"
        private const val type = "master"
    }


    override suspend fun discoverAlbums(apiKey: String, apiSecret: String): Try<List<Album>> =
        withContext(Dispatchers.IO) {
            Try {
                discogsDb.service
                    .discoverAlbums(
                        apiKey = apiKey,
                        apiSecret = apiSecret,
                        style = style,
                        type = type,
                        itemsPerPage = ITEMS_PER_PAGE,
                        page = Random.nextInt(500)
                    ).await()
            }
                .map { it.results }
                .map { it.map{it.toDomain()} }
        }


    override suspend fun getAlbumById(albumId: Long): Try<Album> =
        withContext(Dispatchers.IO){
            Try{
                discogsDb.service
                    .getAlbumsById(albumId = albumId)
                    .await()
            }
                .map { it.toDomain() }
        }

}




