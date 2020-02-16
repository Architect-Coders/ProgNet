package com.davidbragadeveloper.prognet.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Album")
data class RoomAlbum(
    @PrimaryKey val id: Long,
    val title: String,
    val year: String,
    val coverImage: String,
    val country: String,
    val heared: Boolean,
    val latitude: RoomCoordinate?,
    val longitude: RoomCoordinate?
)
