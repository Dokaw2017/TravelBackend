package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.util.*

data class Schedule(
    @BsonId
    val id:String = ObjectId().toString(),
    val time:Long,
    val Day:String,
    val date: Date
)
