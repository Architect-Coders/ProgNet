package com.davidbragadeveloper.prognet.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Album")
data class RoomAlbum(
    @PrimaryKey val id: Long,
    val title: String,
    val year: String,
    val coverImage: String,
    val country: String
)
