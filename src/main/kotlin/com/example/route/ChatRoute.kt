package com.example.route

import com.example.data.websocket.WsClientMessage
import com.example.utils.Constants.DEFAULT_PAGE_SIZE
import com.example.utils.QueryParams
import com.example.service.chat.ChatController
import com.example.service.chat.ChatService
import com.example.service.chat.ChatSession
import com.example.utils.WebSocketObject
import com.example.utils.fromJsonOrNull
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach
import org.koin.java.KoinJavaComponent.inject


fun Route.getMessagesForChat(
    chatService: ChatService
) {
    authenticate {
        get("/api/chat/messages") {
            val chatId = call.parameters[QueryParams.PARAM_CHAT_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val page = call.parameters[QueryParams.PARAM_PAGE]?.toIntOrNull() ?: 0
            val pageSize = call.parameters[QueryParams.PARAM_PAGE_SIZE]?.toIntOrNull() ?: DEFAULT_PAGE_SIZE

            println("OWN USER ID: ${call.userId}")
            println("CHAT ID: ${chatId}")

            if (!chatService.doesTheChatBelongsToUser(chatId, call.userId)) {
                call.respond(HttpStatusCode.Forbidden)
                return@get
            }

            val messages = chatService.getMessagesForChat(chatId, page, pageSize)
            call.respond(HttpStatusCode.OK, messages)
        }
    }
}

fun Route.getChatsForUser(
    chatService: ChatService
) {
    authenticate {
        get("/api/chats") {

            val chats = chatService.getChatsForUser(call.userId)
            println("helloooooooo $chats")
            call.respond(HttpStatusCode.OK, chats)
        }
    }
}

fun Route.chatWebSocket(
    chatController: ChatController
) {
    authenticate {
        webSocket("/api/chat/websocket") {
            println("Trying to connect")
            val session = call.sessions.get<ChatSession>()
            if (session == null) {
                close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No sessions"))
                return@webSocket
            }

            chatController.onJoin(session, this)
            println("Connected")
            try {
                incoming.consumeEach { frame ->
                    when (frame) {
                        is Frame.Text -> {
                            val frameText = frame.readText()
                            println("Frame recieved $frameText")
                            val delimiterIndex = frameText.indexOf("#")
                            if (delimiterIndex == -1) {
                                return@consumeEach
                            }
                            val type = frameText.substring(0, delimiterIndex).toIntOrNull() ?: return@consumeEach

                            val json = frameText.substring(delimiterIndex + 1, frameText.length)

                            handleWebSocket(call.userId, chatController, type, frameText,json)
                        }
                        else -> Unit
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                chatController.onDisconnect(session.userId)
            }

        }
    }


}

suspend fun handleWebSocket(
   userId:String,
    chatController: ChatController,
    type: Int,
   frameText:String,
    json: String
) {
    val gson by inject<Gson>(Gson::class.java)

    when (type) {
        WebSocketObject.MESSAGE.ordinal -> {
            val message = gson.fromJsonOrNull(json, WsClientMessage::class.java) ?: return
            chatController.sendMessage(
                userId,frameText, message
            )
        }
    }
}