package com.davidbragadeveloper.prognet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomAlbum::class], version = 1)
abstract class ProgNetDatabase : RoomDatabase() {

    abstract fun albumDao(): RoomAlbumDao

}
