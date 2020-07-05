package com.batu.callstatistics.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "call_table")
data class Call (
    @PrimaryKey var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "phone_number") var phoneNumber: String? = null,
    @ColumnInfo(name = "call_type") var callType: String? = null,
    @ColumnInfo(name = "call_date") var callDate: Long = 0,
    @ColumnInfo(name = "call_duration") var callDuration:Long = 0,
    @ColumnInfo(name = "user_phone_number") var userPhoneNumber: String? = null,
    @ColumnInfo(name = "company_id") var companyId: String? = null
)
