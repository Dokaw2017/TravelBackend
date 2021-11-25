package com.example.service

import com.example.data.models.Chat
import com.example.data.models.Message
import com.example.data.repository.chat.ChatRepository

class ChatService(
    private val repository: ChatRepository
) {

    suspend fun doesTheChatBelongsToUser(chatId: String,userId: String):Boolean{
            return repository.doesChatBelongsToUser(chatId,userId)
    }

    suspend fun getMessagesForChat(chatId:String,page:Int,pageSize:Int):List<Message>{
        return repository.getMessagesForChat(chatId,page,pageSize)
    }

    suspend fun getChatsForUser(userId:String):List<Chat>{
        return repository.getChatForUser(userId)
    }
}