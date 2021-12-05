package com.example.data.repository.chat

import com.example.data.models.Message
import com.example.data.response.ChatResponse

interface ChatRepository {

    suspend fun doesChatBelongsToUser(chatId: String,userId: String):Boolean

    suspend fun getMessagesForChat(chatId:String,page:Int,pageSize:Int):List<Message>

    suspend fun getChatForUser(userId:String):List<ChatResponse>

    suspend fun insertMessage(message: Message)

    suspend fun insertChat(userId1: String,userId2: String,messageId:String)

    suspend fun doesChatWithUserExist(userId1: String,userId2:String):Boolean

    suspend fun updateLastMessageIdForChat(chatId:String,lastMessageId:String)
}