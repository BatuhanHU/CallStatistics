package com.batu.callstatistics.utility

import android.content.Context
import android.util.Log
import com.batu.callstatistics.database.Call
import com.batu.callstatistics.database.item.CardObject
import com.batu.callstatistics.database.item.DataType

class CallStatisticsUtil {
    companion object{

        val TAG = ::CallStatisticsUtil.toString()

        private lateinit var byDuration:List<Pair<String,CardObject>>

        fun longestTalked(context: Context):List<Pair<String, CardObject>>{
            if (this::byDuration.isInitialized) return byDuration
            val map = CdrUtil.getCallsByDuration(context)
            val list =map.toList().sortedBy{ (_, value) -> value.totalDuration}.reversed()

            val nameMap = CdrUtil.getNameMap(context)
            for(itm in list){
                itm.second.type = DataType.DURATION
                if(nameMap.containsKey(itm.second.phoneNumber)) itm.second.fullName = nameMap[itm.second.phoneNumber]!!
            }
            byDuration = list
            return byDuration
        }
    }
}