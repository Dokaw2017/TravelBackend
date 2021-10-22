package com.example.route

import com.example.Utils.Constants.USER_NOT_FOUND
import com.example.Utils.QueryParams
import com.example.data.request.LikeUpdateRequest
import com.example.data.response.ApiResponse
import com.example.service.LikeService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.likePost(
    likeService: LikeService,
){
    authenticate {
        post ("/api/like"){
            val request = call.receiveOrNull<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userId = call.userId
            val likeSuccessful = likeService.likePost(userId,request.postId)
            if (likeSuccessful){
               /* activityService.addLikeActivity(
                    byUserId = userId,
                    parentType = ParentType.fromType(request.parentType),
                    parentId = request.parentId
                )*/
                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse(
                        true,
                        "",
                        ""
                    )
                )
            }else{
                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse(
                        false,
                        USER_NOT_FOUND,
                        ""
                    )
                )
            }

        }
    }
}

fun Route.unlikePost(
    likeService: LikeService
) {
    authenticate {
        delete("/api/unlike") {
            val request = call.receiveOrNull<LikeUpdateRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val unLikeSuccessful = likeService.unLikePost(call.userId, request.postId)
            if (unLikeSuccessful) {
                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse(
                        true,
                        "",
                        ""
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse(
                        false,
                        USER_NOT_FOUND,
                        ""
                    )
                )
            }
        }
    }
}


