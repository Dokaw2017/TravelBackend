package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

data class TravelPlan @OptIn(ExperimentalTime::class) constructor(
    val location:String,
    val duration: Duration,
    val timestamp:Long,
    val participants:Int,
    val to:String,
    val from:String,
    val buddy:String,
    val allAudience:String,
    @BsonId
    val id:String = ObjectId().toString()
)
