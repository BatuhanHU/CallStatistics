package com.batu.callstatistics.database.item

class CardObject(val phoneNumber:String) {

    var type: DataType = DataType.DURATION
    var fullName:String = phoneNumber

    var callCount:Int = 0
    var weekCallCount:Int = 0
    var monthCallCount:Int = 0

    var totalDuration:Int = 0
    var weekDuration:Int = 0
    var monthDuration:Int = 0
}