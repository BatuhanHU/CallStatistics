package com.batu.callstatistics.database

import android.content.Context
import androidx.room.Room

object GetDatabase {
    private var INSTANCE: AppDatabase? = null

    /**
     * gets the database in singleton. DO NOT TOUCH
     */
    fun getDatabase(context: Context): AppDatabase? {
        if (INSTANCE == null) {
            synchronized(AppDatabase::class.java) {
                if (INSTANCE == null) { // Creating Database
                    INSTANCE = Room.databaseBuilder<AppDatabase>(
                        context.applicationContext,
                        AppDatabase::class.java, "app_database"
                    )
                        .build()
                }
            }
        }
        return INSTANCE
    }
}
