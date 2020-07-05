package com.batu.callstatistics.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "call_table")
data class Call (
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "phone_number") var phoneNumber: String = "",
    @ColumnInfo(name = "call_type") var callType: String = "",
    @ColumnInfo(name = "call_date") var callDate: Long = 0,
    @ColumnInfo(name = "call_duration") var callDuration:Int = 0,
    @ColumnInfo(name = "name") var name:String = ""
    )
