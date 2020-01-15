package com.davidbragadeveloper.prognet

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davidbragadeveloper.prognet.data.local.ProgNetDatabase

class ProgNet : Application(){

    val database : ProgNetDatabase by lazy {
        Room.databaseBuilder(
            this,
            ProgNetDatabase::class.java,
            "prog-net-db"
        ).build()
    }

}