package com.example.route

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.UpdateProfileRequest
import com.example.Utils.ApiMessages.FIELD_BLANK
import com.example.Utils.ApiMessages.INVALID_CREDENTIALS
import com.example.Utils.ApiMessages.USER_ALREADY_EXISTS
import com.example.Utils.Constants.BASE_URL
import com.example.Utils.Constants.PROFILE_PICTURE_PATH
import com.example.data.request.LoginRequest
import com.example.data.request.RegistrationRequest
import com.example.data.response.ApiResponse
import com.example.data.response.AuthResponse
import com.example.service.UserService
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.io.File
import java.util.*

/*fun Route.createUser(
    userService: UserService
){

    post ("/api/user/create"){

        val request = call.receiveOrNull<RegistrationRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val userExists = userService.getUserById(request.email) != null
        if (userExists) {
            call.respond(ApiResponse<Unit>(false, USER_ALREADY_EXISTS))
            return@post
        }
        if (request.email.isBlank() || request.username.isBlank() || request.password.isBlank()) {
            call.respond(ApiResponse<Unit>(false, FIELD_BLANK))
            return@post
        }

        userService.createUser(request)

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
                ApiResponse<Unit>(false, INVALID_CREDENTIALS)
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
                ApiResponse<Unit>(false, INVALID_CREDENTIALS)
            )
        }

    }

}*/
fun Route.updateUserProfile(
    userService: UserService
) {
    val gson: Gson by inject()

        put("/api/user/update") {

            val multipart = call.receiveMultipart()
            var updateProfileRequest: UpdateProfileRequest? = null
            var fileName:String? = null
            multipart.forEachPart {partData ->
                when(partData){
                    is PartData.FormItem->{
                        if (partData.name == "update_profile_data"){
                            updateProfileRequest = gson.fromJson(
                                partData.value,
                                UpdateProfileRequest::class.java)
                        }
                    }
                    is PartData.FileItem->{
                        val fileBytes = partData.streamProvider().readBytes()
                        val fileExtension = partData.originalFileName?.takeLastWhile { it != '.' }
                        fileName = UUID.randomUUID().toString() + "." + fileExtension
                        //Paths.get("").toAbsolutePath().toString()
                        File("src/main/${PROFILE_PICTURE_PATH}/$fileName").writeBytes(fileBytes)
                        //fileName = partData.save(PROFILE_PICTURE_PATH)
                    }
                    is PartData.BinaryItem->Unit
                }
            }
            //val profilePictureUrl = "${BASE_URL}profile_pictures/$fileName"
            val profilePictureUrl = "${BASE_URL}src/main/$PROFILE_PICTURE_PATH$fileName"


            updateProfileRequest?.let {request->

                val updateAcknowledged = userService.updateUser(
                    userId = call.userId,
                    profileImageUrl = profilePictureUrl,
                    updateProfileRequest = request
                )

                if (updateAcknowledged){
                    call.respond(HttpStatusCode.OK,ApiResponse<Unit>(true,""))
                }else{
                    File("${PROFILE_PICTURE_PATH}/$fileName").delete()
                    call.respond(HttpStatusCode.InternalServerError)
                }
            } ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

        }
    }

