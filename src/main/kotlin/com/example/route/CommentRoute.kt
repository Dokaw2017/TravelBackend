package com.example.route

import com.example.Utils.ApiMessages.COMMENT_TOO_LONG
import com.example.Utils.ApiMessages.FIELD_BLANK
import com.example.Utils.QueryParams
import com.example.data.request.CreateCommentRequest
import com.example.data.request.DeleteCommentRequest
import com.example.data.response.ApiResponse
import com.example.service.CommentService
import com.example.service.LikeService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.createComment(
    commentService: CommentService,
){
    authenticate {
        post ("/api/comment/create"){
            val request = call.receiveOrNull<CreateCommentRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val userId = call.userId
            when(commentService.createComment(request,userId)){
                is CommentService.ValidationEvent.ErrorFieldEmpty->{
                    call.respond(
                        HttpStatusCode.OK,
                        ApiResponse(
                            false,
                            FIELD_BLANK,
                            ""
                        )
                    )
                }
                is CommentService.ValidationEvent.CommentTooLong->{
                    call.respond(
                        HttpStatusCode.OK,
                        ApiResponse(
                            false,
                            COMMENT_TOO_LONG,
                            ""
                        )
                    )
                }
                is CommentService.ValidationEvent.Success->{
                    call.respond(
                        HttpStatusCode.OK,
                        ApiResponse(
                            true,
                            "",
                            ""
                        )
                    )
                }
                is CommentService.ValidationEvent.UserNotFound->{
                    call.respond(
                        HttpStatusCode.OK,
                        ApiResponse<Unit>(
                            false,
                            message = "User not found"
                        )
                    )
                }
            }
        }
    }
}

fun Route.getCommentsForPost(
    commentService: CommentService,
){
    authenticate {
        get ("/api/comment/get"){
            val postId = call.parameters[QueryParams.PARAM_POST_ID] ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val comments = commentService.getCommentsForPost(postId,call.userId)
            call.respond(HttpStatusCode.OK,comments)
            println("Hello : ${comments}")

        }
    }
}

fun Route.deleteComment(
    commentService: CommentService,
    likeService: LikeService
){
    authenticate {
        delete ("/api/comment/delete"){
            val request = call.receiveOrNull<DeleteCommentRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val comment = commentService.getCommentById(request.commentId)
            if (comment?.userId != call.userId){
                call.respond(HttpStatusCode.Unauthorized)
                return@delete
            }
            val deleted = commentService.deleteComment(request.commentId)
            if (deleted){
                likeService.deleteLikesForParent(request.commentId)
                call.respond(HttpStatusCode.OK,ApiResponse(true,"",""))
            }else{
                call.respond(HttpStatusCode.NotFound,ApiResponse(false,"",""))
            }
        }
    }
}