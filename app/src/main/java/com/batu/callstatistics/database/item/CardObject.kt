package com.batu.callstatistics.database.item

class CardObject(val phoneNumber:String) {

    var type: DataType = DataType.DURATION
    var fullName:String = phoneNumber
    var totalDuration:Int = 0
    var callCount:Int = 0
}