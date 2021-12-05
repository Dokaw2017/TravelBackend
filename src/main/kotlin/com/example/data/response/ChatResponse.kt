package com.example.data.response

data class ChatResponse(
    val chatId:String,
    val remoteUserId:String?,
    val remoteUsername:String?,
    val remoteUserProfileUrl:String?,
    val lastMessage:String?,
    val timestamp:Long?
)
