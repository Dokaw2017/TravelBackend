package com.example.service.chat

import com.example.data.models.Chat
import com.example.data.models.Message
import com.example.data.repository.chat.ChatRepository
import com.example.data.websocket.WsMessage
import io.ktor.http.cio.websocket.*
import java.util.concurrent.ConcurrentHashMap

class ChatController(
    private val repository: ChatRepository
) {

    private val onlineUsers = ConcurrentHashMap<String, WebSocketSession>()

    fun onJoin(chatSession: ChatSession, socket: WebSocketSession) {
        onlineUsers[chatSession.userId] = socket
    }

    fun onDisconnect(userId:String){
        if (onlineUsers.containsKey(userId)){
            onlineUsers.remove(userId)
        }
    }

    suspend fun sendMessage(json:String,message: WsMessage){
        onlineUsers[message.toId]?.send(Frame.Text(json))
        onlineUsers[message.fromId]?.send(Frame.Text(json))

        val messageEntity = message.toMessage()

        repository.insertMessage(message.toMessage())

        if (!repository.doesChatWithUserExist(message.fromId,message.toId)){
            repository.insertChat(message.fromId,message.toId,messageEntity.id
            )
        }else{
            message.chatId?.let {
                repository.updateLastMessageIdForChat(message.chatId,messageEntity.id)
            }

        }

    }
}