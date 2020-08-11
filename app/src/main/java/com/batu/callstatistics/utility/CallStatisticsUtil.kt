package com.batu.callstatistics.utility

import android.content.Context
import android.util.Log
import com.batu.callstatistics.database.Call

class CallStatisticsUtil {
    companion object{

        val TAG = ::CallStatisticsUtil.toString()

        private lateinit var byDuration:List<Pair<String,Int>>

        fun longestTalked(context: Context):List<Pair<String, Int>>{
            if (this::byDuration.isInitialized) return byDuration
            val map = CdrUtil.getCallsByDuration(context)
            val list =map.toList().sortedBy{ (_, value) -> value}.reversed()
            byDuration = list
            return byDuration
        }
    }
}