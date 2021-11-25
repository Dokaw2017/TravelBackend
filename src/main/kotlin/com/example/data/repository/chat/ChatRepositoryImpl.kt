package com.example.data.repository.chat

import com.example.data.models.Chat
import com.example.data.models.Message
import com.example.data.models.SimpleUser
import com.example.data.models.User
import org.litote.kmongo.contains
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class ChatRepositoryImpl(
    db:CoroutineDatabase
):ChatRepository {
   private val chats = db.getCollection<Chat>()
   private val users = db.getCollection<User>()
   private val messages = db.getCollection<Message>()

    override suspend fun getMessagesForChat(chatId: String,page:Int,pageSize:Int): List<Message> {
        return messages.find(Message::chatId eq chatId)
            .skip(page*pageSize)
            .limit(pageSize)
            .descendingSort(Message::timestamp)
            .toList()
    }

    override suspend fun getChatForUser(userId: String): List<Chat> {
        val user = users.findOneById(userId) ?: return emptyList()
        val simpleUser = SimpleUser(
            id = user.id,
            profilePictureUrl = user.profileImageUrl,
            username = user.username
        )
       return chats.find(Chat::userId contains simpleUser)
           .descendingSort(Chat::timestamp)
           .toList()
    }

    override suspend fun doesChatBelongsToUser(chatId: String, userId: String): Boolean {
        return chats.findOneById(chatId)?.userId?.any{it.id == userId} == true
    }
}