package com.example.route

import com.example.Utils.ApiMessages.USER_NOT_FOUND
import com.example.data.repository.follow.FollowRepository
import com.example.data.request.FollowUpdateRequest
import com.example.data.response.ApiResponse
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*


fun Route.followUser(followRepository: FollowRepository){
    post ("/api/following/follow"){
        val request = call.receiveOrNull<FollowUpdateRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }
        val didUserExist = followRepository.followUser(
            request.followingUserId,
            request.followedUserId
        )

        if (didUserExist){
            call.respond(HttpStatusCode.OK, ApiResponse(
                 true,
            )
            )
        }else{
            call.respond(HttpStatusCode.OK,ApiResponse(
                false,
                message = USER_NOT_FOUND
            ))
        }

    }
}

fun Route.unFollowUser(followRepository: FollowRepository){
    delete ("/api/following/unfollow"){
        val request = call.receiveOrNull<FollowUpdateRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }
        val didUserExist = followRepository.unFollowUser(
            request.followingUserId,
            request.followedUserId
        )

        if (didUserExist){
            call.respond(HttpStatusCode.OK,ApiResponse(
                true
            ))
        }else{
            call.respond(HttpStatusCode.OK,ApiResponse(
                false,
                message = USER_NOT_FOUND
            ))
        }
    }
}