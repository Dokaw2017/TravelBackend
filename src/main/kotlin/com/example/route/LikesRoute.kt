package com.example.route

import com.example.utils.Constants.USER_NOT_FOUND
import com.example.utils.QueryParams
import com.example.data.request.LikeUpdateRequest
import com.example.data.response.ApiResponse
import com.example.service.LikeService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.likeParent(
    likeService: LikeService,
) {
    authenticate {
        post("/api/like") {
            val request = call.receiveOrNull<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val userId = call.userId
            val likeSuccessful = likeService.likeParent(userId, request.parentId, request.parentType)
            if(likeSuccessful) {
                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse<Unit>(
                        success = true
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse<Unit>(
                        success = false,
                        message = USER_NOT_FOUND
                    )
                )
            }
        }
    }
}

fun Route.unlikeParent(
    likeService: LikeService,
) {
    authenticate {
        delete("/api/unlike") {
            val parentId = call.parameters[QueryParams.PARAM_PARENT_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val parentType = call.parameters[QueryParams.PARAM_PARENT_TYPE]?.toIntOrNull() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val unlikeSuccessful = likeService.unlikeParent(call.userId, parentId, parentType)
            if(unlikeSuccessful) {
                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse<Unit>(
                        success = true
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse<Unit>(
                        success = false,
                        message = USER_NOT_FOUND
                    )
                )
            }
        }
    }
}

fun Route.getLikesForParent(likeService: LikeService) {
    authenticate {
        get("/api/like/parent") {
            val parentId = call.parameters[QueryParams.PARAM_PARENT_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val usersWhoLikedParent = likeService.getUsersWhoLikedParent(
                parentId = parentId,
                call.userId
            )
            call.respond(
                HttpStatusCode.OK,
                usersWhoLikedParent
            )
        }
    }
}

