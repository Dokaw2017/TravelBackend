package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.*

data class Plan(

    val userId:String,
    val evenId:Event,
    val scheduleId:Schedule,
    val buddyId:String,
    val participants:Int,
    val location:String,
    @BsonId
    val id:String = ObjectId().toString(),
)
