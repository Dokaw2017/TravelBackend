package com.example.data.models

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class Chat(
    val userId:List<SimpleUser>,
    val lastMessage: Message,
    val timestamp:Long,
    @BsonId
    val id:String = ObjectId().toString()
)
