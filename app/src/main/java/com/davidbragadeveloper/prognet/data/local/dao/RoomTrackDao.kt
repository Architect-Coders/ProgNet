package com.davidbragadeveloper.prognet.data.local.dao

import androidx.room.*
import com.davidbragadeveloper.domain.Track

import com.davidbragadeveloper.prognet.data.local.entities.RoomTrack


@Dao
interface RoomTrackDao{
    @Insert
    fun insert(repo: RoomTrack?)

    @Update
    fun update(tracks: List<RoomTrack>)

    @Delete
    fun delete(tracks: List<RoomTrack>)

    @Query("SELECT * FROM Track")
    fun getAllTracks(): List<Track>?

    @Query("SELECT * FROM Track WHERE albumId=:albumId")
    fun findRepositoriesForUser(albumId: Long): List<Track?>?
}