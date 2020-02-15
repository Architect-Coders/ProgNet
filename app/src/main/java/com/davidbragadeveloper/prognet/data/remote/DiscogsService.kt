package com.davidbragadeveloper.prognet.data.remote

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DiscogsService {
    @GET("database/search")
    fun discoverAlbums(
        @Query("key") apiKey: String,
        @Query("secret") apiSecret: String,
        @Query("style") style: String,
        @Query("type") type: String,
        @Query("per_page") itemsPerPage: Int,
        @Query("page") page: Int
    ): Deferred<DiscogsAlbumsResult>

    @GET("masters/{album_id}")
    fun getAlbumsById(
        @Path("album_id") albumId: Long
    ): Deferred<DiscogsAlbum>
}
