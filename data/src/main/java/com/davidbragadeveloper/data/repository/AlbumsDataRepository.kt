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
) : AlbumsRepository{

    override suspend fun dicoverAlbums(): Try<List<Album>> =
        remoteDataSource
            .discoverAlbums(apiKey = apiKey, apiSecret = apiSecret)
            .also { localDataSource.saveNotSavedAlbums(it.getOrDefault {listOf()} )}

    override suspend fun getAlbumById(albumId: Long): Try<Album> {
        val expectedLocalAlbum =
            localDataSource
            .findById(id = albumId)

        return if(expectedLocalAlbum.exists { !it.tracks.isNullOrEmpty() } ){
            expectedLocalAlbum
        } else{
            remoteDataSource
                .getAlbumById(albumId = albumId)
                .map { remoteAlbum ->
                    val localAlbum = expectedLocalAlbum.getOrDefault{ remoteAlbum }
                    localAlbum.merge(remoteAlbum)
                }
                .also { it.getOrDefault{null}?.let{
                    localDataSource.saveAlbums(listOf(it))
                }}
        }

    }

    override suspend fun toggleAlbumHeared(album: Album): Try<Boolean> =
        localDataSource.updateHeared(album)



    private fun Album.merge(other: Album): Album =
        if(id != other.id){
            this
        } else {
            copy(
                id= id,
                title = if(title.isBlank()) other.title else title,
                year =  if(year.isBlank()) other.year else year,
                coverImage = if(coverImage.isBlank()) other.coverImage else coverImage,
                country = if(country.isBlank()) other.country else country,
                tracks = if(tracks.isNullOrEmpty()) other.tracks else tracks,
                heared = heared,
                location = location
            )
        }
}

