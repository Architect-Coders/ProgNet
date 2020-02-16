package com.davidbragadeveloper.prognet.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "Track",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = RoomAlbum::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("albumId"),
            onDelete = CASCADE
        )
    )
)
data class RoomTrack(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    val duration: String,
    val position: String,
    val title: String,
    val albumId: Long
)

