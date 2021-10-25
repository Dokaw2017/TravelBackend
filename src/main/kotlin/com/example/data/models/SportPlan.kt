package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

data class SportPlan (
    val title:String,
    val place:String,
    val date:String,
    val userId:String,
    val startTime: String,
    val finishTime:String,
    val participants:Int,

    @BsonId
    val id:String = ObjectId().toString()
)
