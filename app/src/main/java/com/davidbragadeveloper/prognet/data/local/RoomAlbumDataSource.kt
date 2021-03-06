package com.davidbragadeveloper.prognet.data.local

import arrow.core.Try
import com.davidbragadeveloper.data.source.AlbumLocalDataSource
import com.davidbragadeveloper.domain.Album
import com.davidbragadeveloper.prognet.data.mappers.toDomain
import com.davidbragadeveloper.prognet.data.mappers.toRoomAlbum
import com.davidbragadeveloper.prognet.data.mappers.toRoomTrackList

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomAlbumDataSource(private val dataBase: ProgNetDatabase) : AlbumLocalDataSource{

    private val albumDao = this.dataBase.albumDao()
    private val trackDao = this.dataBase.trackDao()

    override suspend fun isEmpty(): Try<Boolean> =
        withContext(Dispatchers.IO){ Try{ albumDao.albumCount() <= 0} }

    override suspend fun saveAlbums(albums: List<Album>) =
        withContext(Dispatchers.IO){
            albumDao
                .insertAlbums(albums= albums.map{it.toRoomAlbum()})
            albums.forEach { album ->
                trackDao.insertTrack(track = album.toRoomTrackList())
            }
        }

    override suspend fun saveNotSavedAlbums(albums: List<Album>) =
        withContext(Dispatchers.IO){
            albumDao.insertAlbumsIfAreNotSet(albums = albums.map{it.toRoomAlbum()})
        }

    override suspend fun getAlbums(): Try<List<Album>> =
        withContext(Dispatchers.IO){
            Try{
                albumDao.getAll().map{
                    it
                        .toDomain()
                        .copy(tracks = trackDao.findTrackByAlbumId(it.id))
                }
            }
        }

    override suspend fun findById(id: Long): Try<Album> =
        withContext(Dispatchers.IO){
            Try{
                albumDao.findById(id)
                    .toDomain()
                    .copy(tracks = trackDao.findTrackByAlbumId(id))
            }
        }

    override suspend fun updateHeared(album: Album):Try<Boolean> =
        withContext(Dispatchers.IO){
            Try {
                val roomAlbum = album.toRoomAlbum()
                albumDao.updateAlbum(album = roomAlbum)
                albumDao.isAlbumHeared(id = roomAlbum.id)
            }
        }

}
