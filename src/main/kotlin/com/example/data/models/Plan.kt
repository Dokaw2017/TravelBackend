package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId


data class Plan (
    val title:String,
    val from:String,
    val to:String,
    val date:String,
    val userId:String,
    val time: String,
    val category: String,
    val subCategory:String,
    val isBuddy:Boolean,
    @BsonId
    val id:String = ObjectId().toString()
)
