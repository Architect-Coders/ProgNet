package com.davidbragadeveloper.prognet.model

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscogsService {
    @GET("database/search")
    fun discoverProgRockAlbums(
        @Query("key") apiKey: String,
        @Query("secret") apiSecret: String,
        @Query("style") style: String,
        @Query("per_page") itemsPerPage: Int,
        @Query("page") page: Int
    ): Deferred<ProgRockAlbumsResult>
}
