package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Like(
    val parentId:String,
    val userId:String,
    val parentType:Int,
    val timestamp:Long,
    @BsonId
    val id:String = ObjectId().toString()
)
