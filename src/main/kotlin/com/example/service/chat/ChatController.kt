package com.example.service.chat

import com.example.data.repository.chat.ChatRepository
import com.example.data.websocket.WsClientMessage
import com.example.data.websocket.WsServerMessage
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

    suspend fun sendMessage(userId: String,frameText:String,message: WsClientMessage){
        onlineUsers[message.toId]?.send(Frame.Text(frameText))
        onlineUsers[userId]?.send(Frame.Text(frameText))

        val messageEntity = message.toMessage(userId)

        repository.insertMessage(messageEntity)

        if (!repository.doesChatWithUserExist(userId,message.toId)){
            repository.insertChat(userId,message.toId,messageEntity.id
            )
        }else{
            message.chatId?.let {
                repository.updateLastMessageIdForChat(message.chatId,messageEntity.id)
            }

        }

    }
}