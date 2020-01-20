package com.davidbragadeveloper.prognet.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RoomAlbum::class], version = 1)
abstract class ProgNetDatabase : RoomDatabase() {

    companion object{
        fun build(context: Context): ProgNetDatabase =
            Room.databaseBuilder(
                context,
                ProgNetDatabase::class.java,
                "prog-net-db"
            ).build()

    }

    abstract fun albumDao(): RoomAlbumDao


}
