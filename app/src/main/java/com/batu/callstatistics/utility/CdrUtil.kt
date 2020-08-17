package com.batu.callstatistics.utility

import android.annotation.SuppressLint
import android.content.Context
import android.provider.CallLog
import android.util.Log
import com.batu.callstatistics.database.Call
import com.batu.callstatistics.database.GetDatabase
import com.batu.callstatistics.database.item.CardObject
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList


class CdrUtil {
    companion object{
        val TAG = "CDRUTIL"

        lateinit var names:HashMap<String, String>

        /**
         * Returns the arrayList of Cdr data for last given minutes.
         */
        fun getLastCDRData(lastMinutes:Long, context: Context):ArrayList<Call>{

            val returnList = ArrayList<Call>()
            // Sorting with Date Desc for getting last call at first
            @SuppressLint("MissingPermission") val managedCursor = context
                .getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, "DATE DESC")
            val db = GetDatabase.getDatabase(context)
            val number = managedCursor!!.getColumnIndex(CallLog.Calls.NUMBER)
            val type = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
            val date = managedCursor.getColumnIndex(CallLog.Calls.DATE)
            val duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
            val name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME)

            while (managedCursor.moveToNext()) {
                val callDate = java.lang.Long.valueOf(managedCursor.getString(date))
                // Breaking the search for calls that older than keep alive time
                if (callDate + (lastMinutes * 60 * 1000) <= Date().time) {
                    Log.d(TAG, "Last call limit exceeded, breaking")
                    break
                }
                //val phNumber = PhoneUtil.fixPhoneNumber(managedCursor.getString(number))
                val phNumber = managedCursor.getString(number)
                val callType = managedCursor.getString(type)
                val callDuration = Integer.valueOf(managedCursor.getString(duration))
                val dirName = managedCursor.getString(name)
                val dir: String
                dir = when (Integer.parseInt(callType)) {
                    CallLog.Calls.OUTGOING_TYPE -> "2"

                    CallLog.Calls.INCOMING_TYPE -> "1"

                    CallLog.Calls.MISSED_TYPE -> "3"

                    CallLog.Calls.REJECTED_TYPE -> "4"
                    else -> CallLog.Calls.TYPE
                }

                val call = Call()
                call.callDate = callDate
                call.callDuration = callDuration
                call.callType = dir
                call.phoneNumber = phNumber
                call.name = dirName

                returnList.add(call)
            }
            managedCursor.close()
            return returnList
        }

        fun getNameMap(context: Context):HashMap<String, String>{
            if(this::names.isInitialized) return names
            Log.i(TAG, "Filling the name hashmap")
            @SuppressLint("MissingPermission") val managedCursor = context
                .getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, "DATE DESC")

            val number = managedCursor!!.getColumnIndex(CallLog.Calls.NUMBER)
            val name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME)
            val nameMap:HashMap<String, String> = hashMapOf()

            while (managedCursor.moveToNext()) {
                val dirName = managedCursor.getString(name)
                var dirNum = managedCursor.getString(number)
                if (dirNum == "-2" || dirName.isNullOrBlank()) {
                    continue
                }
                if (dirNum.length > 3) {
                    if (dirNum.substring(0, 3) == "+90") {
                        dirNum = dirNum.substring(2)
                    }
                }
                nameMap[dirNum] = dirName
            }
            names = nameMap
            return names
        }
        fun getCallsByDuration(context: Context):HashMap<String, CardObject>{
            Log.i(TAG, "Getting people by total duration")
            @SuppressLint("MissingPermission") val managedCursor = context
                .getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, "DATE DESC")

            val number = managedCursor!!.getColumnIndex(CallLog.Calls.NUMBER)
            val duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
            val date = managedCursor.getColumnIndex(CallLog.Calls.DATE)

            val nameMap:HashMap<String, CardObject> = hashMapOf()
            val lastWeek = lastWeek().time
            val lastMonth = lastMonth().time

            while (managedCursor.moveToNext()) {
                var dirNum = managedCursor.getString(number)
                var dirDuration = managedCursor.getInt(duration)
                val callDate = java.lang.Long.valueOf(managedCursor.getString(date))
                if (dirNum == "-2") {
                    continue
                }
                if (dirNum.length > 3) {
                    if (dirNum.substring(0, 3) == "+90") {
                        dirNum = dirNum.substring(2)
                    }
                }
                val current = nameMap.getOrPut(dirNum){CardObject(dirNum)}
                nameMap[dirNum]!!.totalDuration = current.totalDuration + dirDuration
                nameMap[dirNum]!!.callCount++

                if(callDate >= lastWeek){
                    nameMap[dirNum]!!.weekDuration = current.weekDuration + dirDuration
                    nameMap[dirNum]!!.weekCallCount++
                }
                if(callDate  >= lastMonth) {
                    nameMap[dirNum]!!.monthDuration = current.monthDuration + dirDuration
                    nameMap[dirNum]!!.monthCallCount++
                }

            }
            return nameMap
        }

        private fun lastWeek(): Date {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -7)
            return cal.time
        }

        private fun lastMonth(): Date {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -30)
            return cal.time
        }

    }
}