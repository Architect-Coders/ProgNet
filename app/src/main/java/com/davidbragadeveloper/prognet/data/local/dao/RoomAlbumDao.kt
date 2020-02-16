package com.davidbragadeveloper.prognet.data.local.dao

import androidx.room.*
import com.davidbragadeveloper.prognet.data.local.entities.RoomAlbum

@Dao
interface RoomAlbumDao {

    @Query("SELECT * FROM Album")
    fun getAll(): List<RoomAlbum>

    @Query("SELECT * FROM Album WHERE id = :id")
    fun findById(id: Long): RoomAlbum

    @Query("SELECT COUNT(id) FROM Album")
    fun albumCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAlbums(albums: List<RoomAlbum>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAlbumsIfAreNotSet(albums: List<RoomAlbum>)

    @Update
    fun updateAlbum(album: RoomAlbum)

}
