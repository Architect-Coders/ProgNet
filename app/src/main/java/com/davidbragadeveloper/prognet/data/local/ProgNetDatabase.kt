package com.davidbragadeveloper.prognet.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davidbragadeveloper.prognet.data.local.dao.RoomAlbumDao
import com.davidbragadeveloper.prognet.data.local.dao.RoomTrackDao
import com.davidbragadeveloper.prognet.data.local.entities.RoomAlbum
import com.davidbragadeveloper.prognet.data.local.entities.RoomTrack

@Database(entities = [RoomAlbum::class, RoomTrack::class], version = 1)
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

    abstract fun trackDao(): RoomTrackDao
}
