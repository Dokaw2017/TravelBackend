package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class Event (
    val title:String,
    val place:String,
    val date:String,
    val userId:String,
    val time: String,
    val category:String,
    val isBuddy:Boolean,
    @BsonId
    val id:String = ObjectId().toString()
)
