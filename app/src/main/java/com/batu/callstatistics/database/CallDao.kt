package com.batu.callstatistics.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CallDao {

    @Query("SELECT * from  call_table ")
    fun getAllCalls(): List<Call>

    @Query("SELECT * from call_table ORDER BY call_date DESC")
    fun getAllCallsSortedDesc(): List<Call>

    @Query("SELECT * from call_table ORDER BY call_date ASC")
    fun getAllCallsSortedAsc(): List<Call>

    @Insert
    fun insertAll(vararg users: Call)

    @Delete
    fun delete(user: Call)
}