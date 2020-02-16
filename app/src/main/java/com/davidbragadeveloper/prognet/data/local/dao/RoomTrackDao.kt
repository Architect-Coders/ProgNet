package com.davidbragadeveloper.prognet.data.local.dao

import androidx.room.*
import com.davidbragadeveloper.domain.Track

import com.davidbragadeveloper.prognet.data.local.entities.RoomTrack

@Dao
interface RoomTrackDao{
    @Insert
    fun insertTrack(track: List<RoomTrack>)

    @Update
    fun updateTrack(tracks: List<RoomTrack>)

    @Delete
    fun deleteTrack(tracks: List<RoomTrack>)

    @Query("SELECT * FROM Track")
    fun getAllTracks(): List<Track>

    @Query("SELECT * FROM Track WHERE albumId=:albumId")
    fun findTrackByAlbumId(albumId: Long): List<Track>
}