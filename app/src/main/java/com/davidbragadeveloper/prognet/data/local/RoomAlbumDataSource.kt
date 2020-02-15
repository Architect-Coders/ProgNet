package com.davidbragadeveloper.prognet.data.local

import arrow.core.Try
import com.davidbragadeveloper.data.source.AlbumLocalDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.data.mappers.toDomain
import com.davidbragadeveloper.prognet.data.mappers.toRoomAlbum

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomAlbumDataSource(private val dataBase: ProgNetDatabase) : AlbumLocalDataSource{

    private val albumDao = this.dataBase.albumDao()
    private val trackDao = this.dataBase.trackDao()

    override suspend fun isEmpty(): Try<Boolean> =
        withContext(Dispatchers.IO){ Try{ albumDao.albumCount() <= 0} }

    override suspend fun saveAlbums(albums: List<Album>) =
        withContext(Dispatchers.IO){ albumDao.insertAlbums(albums= albums.map{it.toRoomAlbum()})}

    override suspend fun saveNotSavedAlbums(albums: List<Album>) =
        withContext(Dispatchers.IO){
            albumDao.insertAlbumsIfAreNotSet(albums = albums.map{it.toRoomAlbum()})
        }

    override suspend fun getAlbums(): Try<List<Album>> =
        withContext(Dispatchers.IO){ Try{albumDao.getAll().map{it.toDomain()}} }

    override suspend fun findById(id: Long): Try<Album> =
        withContext(Dispatchers.IO){ Try{albumDao.findById(id).toDomain()}}

    override suspend fun update(album: Album) =
        withContext(Dispatchers.IO){ albumDao.updateAlbum(album = album.toRoomAlbum())}

}
