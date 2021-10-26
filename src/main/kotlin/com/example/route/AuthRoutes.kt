package com.example.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.Utils.ApiMessages
import com.example.data.models.User
import com.example.data.request.LoginRequest
import com.example.data.request.RegistrationRequest
import com.example.data.response.ApiResponse
import com.example.data.response.AuthResponse
import com.example.service.UserService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.util.*


fun Route.createUser(
    userService: UserService
){

    post ("/api/user/create"){

        val request = call.receiveOrNull<RegistrationRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val userExists = userService.getUserById(request.email) != null
        if (userExists) {
            call.respond(ApiResponse<Unit>(false, ApiMessages.USER_ALREADY_EXISTS))
            return@post
        }
        if (request.email.isBlank() || request.username.isBlank() || request.password.isBlank()) {
            call.respond(ApiResponse<Unit>(false, ApiMessages.FIELD_BLANK))
            return@post
        }

        userService.createUser(
          request
        )

        call.respond(
            ApiResponse<Unit>(true,"successfully saved")
        )
    }
}


fun Routing.loginUser(
    userService: UserService,
    jwtIssuer: String,
    jwtAudience: String, jwtSecret: String
){
    post("/api/user/login") {
        val request = call.receiveOrNull<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        if (request.email.isBlank() && request.password.isBlank()) {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val user = userService.getUserByEmail(request.email) ?: kotlin.run {
            call.respond(
                HttpStatusCode.OK,
                ApiResponse<Unit>(false, ApiMessages.INVALID_CREDENTIALS)
            )
            return@post
        }

        val isCorrectPassword = userService.isValidPassword(
            enteredPassword = request.password,
            actualPassword = user.password
        )

        if (isCorrectPassword) {
            val expiresIn = 1000L * 60L * 60L * 24L * 365L
            val token = JWT.create()
                .withClaim("userId", user.id)
                .withIssuer(jwtIssuer)
                .withExpiresAt(Date(System.currentTimeMillis() + expiresIn))
                .withAudience(jwtAudience)
                .sign(Algorithm.HMAC256(jwtSecret))
            call.respond(
                HttpStatusCode.OK,
                ApiResponse(
                    true,
                    data = AuthResponse(token)
                )

            )
        } else {
            call.respond(
                HttpStatusCode.OK,
                ApiResponse<Unit>(false, ApiMessages.INVALID_CREDENTIALS)
            )
        }

    }

}

fun Route.authenticate(){
    authenticate {
        get ("/api/user/authenticate"){
            call.respond(HttpStatusCode.OK)
        }
    }

}
