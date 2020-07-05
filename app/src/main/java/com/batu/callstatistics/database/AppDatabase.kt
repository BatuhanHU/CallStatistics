package com.batu.callstatistics.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Call::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun callDao(): CallDao
}