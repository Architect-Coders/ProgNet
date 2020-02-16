package com.davidbragadeveloper.data.source

import arrow.core.Try
import com.davidbragadeveloper.domain.Album

interface AlbumLocalDataSource {
    suspend fun isEmpty(): Try<Boolean>
    suspend fun saveAlbums(albums: List<Album>)
    suspend fun getAlbums(): Try<List<Album>>
    suspend fun findById(id: Long): Try<Album>
    suspend fun update(album: Album): Try<Boolean>
    suspend fun saveNotSavedAlbums(albums: List<Album>)
}