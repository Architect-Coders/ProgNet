package com.davidbragadeveloper.prognet

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davidbragadeveloper.prognet.data.local.ProgNetDatabase

class ProgNet : Application(){

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}