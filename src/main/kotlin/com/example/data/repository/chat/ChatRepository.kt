package com.example.data.repository.chat

import com.example.data.models.Chat
import com.example.data.models.Message

interface ChatRepository {

    suspend fun doesChatBelongsToUser(chatId: String,userId: String):Boolean

    suspend fun getMessagesForChat(chatId:String,page:Int,pageSize:Int):List<Message>

    suspend fun getChatForUser(userId:String):List<Chat>
}