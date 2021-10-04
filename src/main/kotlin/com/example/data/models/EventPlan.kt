package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

data class EventPlan @OptIn(ExperimentalTime::class) constructor(
    val location:String,
    val duration: Duration,
    val timestamp:Long,
    val participants:Int,

    @BsonId
    val id:String = ObjectId().toString()
)
