package com.example.route

import com.example.Utils.ApiMessages.FIELD_BLANK
import com.example.Utils.ApiMessages.USER_ALREADY_EXISTS
import com.example.data.models.User
import com.example.data.request.RegistrationRequest
import com.example.data.response.ApiResponse
import com.example.repository.UserRepository
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Route.createUser(){

    val userRepository:UserRepository by inject()

    post ("/api/user/create"){

        val request = call.receiveOrNull<RegistrationRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val userExists = userRepository.getUserById(request.email) != null
        if (userExists) {
            call.respond(ApiResponse(false, USER_ALREADY_EXISTS))
            return@post
        }
        if (request.email.isBlank() || request.username.isBlank() || request.password.isBlank()) {
            call.respond(ApiResponse(false, FIELD_BLANK))
            return@post
        }

        userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                firstName = "Zak",
                lastname = "nebi",
                profileImageUrl = "",
                phoneNumber = 12345,
                gender = "F",
                location = listOf("Malminkartanno,Kumpula campus"),
                hobbies = listOf("drinking","fucking"),
                buddyId = listOf("",""),
                inviteId = listOf("",""),
                friendsId = "",
                birthDay = Date(20/3/2020),
                chatGroupId = listOf("",""),
            )
        )
        call.respond(
            ApiResponse(true,"successfully saved")
        )
    }
}

