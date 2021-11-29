package com.example.route

import com.example.utils.Constants.USER_NOT_FOUND
import com.example.utils.QueryParams
import com.example.data.request.FollowRequest
import com.example.data.response.ApiResponse
import com.example.service.FollowService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.followUser(
    followService: FollowService,
    ){
    authenticate {
        post ("/api/following/follow"){
            val request = call.receiveOrNull<FollowRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val didUserExist = followService.followUserIfExists(request,call.userId)

            if (didUserExist){

                call.respond(
                    HttpStatusCode.OK, ApiResponse(
                     true,
                        "",
                        ""
                )
                )
            }else{
                call.respond(
                    HttpStatusCode.OK,ApiResponse(
                    false,
                    USER_NOT_FOUND,
                        ""
                ))
            }

        }
    }

}

fun Route.unFollowUser(followService: FollowService){
    delete ("/api/following/unfollow"){
        val userId = call.parameters[QueryParams.PARAM_USER_ID] ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }
   /*     val request = call.receiveOrNull<FollowRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@delete
        }*/
        val didUserExist = followService.unfollowUserIfExists(userId,call.userId)
        if (didUserExist){
            call.respond(
                HttpStatusCode.OK,ApiResponse(
                true,
                    "",
                    ""
            ))
        }else{
            call.respond(
                HttpStatusCode.OK,ApiResponse(
                false,
                USER_NOT_FOUND,
                    ""
            ))
        }
    }
}