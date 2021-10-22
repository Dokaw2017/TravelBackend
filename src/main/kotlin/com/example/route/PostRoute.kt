package com.example.route

import com.example.Utils.Constants
import com.example.Utils.Constants.DEFAULT_PAGE_SIZE
import com.example.Utils.QueryParams
import com.example.data.request.CreatePostRequest
import com.example.data.request.DeletePostRequest
import com.example.data.response.ApiResponse
import com.example.service.LikeService
import com.example.service.PostService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlin.jvm.internal.Intrinsics

fun Route.cratePostRoute(
    postService: PostService
){
    post("/api/post/create") {
        val request = call.receiveOrNull<CreatePostRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest)
            return@post
        }

        val didUserExist = postService.createPost(request,call.userId,imageUrl = "")

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
                HttpStatusCode.OK, ApiResponse(
                    false,
                    Constants.USER_NOT_FOUND,
                    ""
                )
            )
        }
    }
}

fun Route.getPostsForFollows(
    postService: PostService,
){
    authenticate {
        get("/api/post/get") {

            val page = call.parameters[QueryParams.PARAM_PAGE]?.toIntOrNull() ?: 0
            val pageSize = call.parameters[QueryParams.PARAM_PAGE_SIZE]?.toIntOrNull() ?: DEFAULT_PAGE_SIZE

            val posts = postService.getPostsForFollows(call.userId,page,pageSize)
            call.respond(
                HttpStatusCode.OK,
                posts
            )

        }
    }
}

fun Route.deletePost(
    postService: PostService,
    likeService: LikeService,
    //commentService: CommentService
){
    authenticate {
        delete ("/api/post/delete"){
            val request = call.receiveOrNull<DeletePostRequest>() ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val post = postService.getPost(request.postId)
            if (post == null){
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }

            if (post.userId == call.userId){
                postService.deletePost(request.postId)
                likeService.deleteLikesForPost(request.postId)

                //commentService.deleteCommentsForPost(request.postId)
                call.respond(HttpStatusCode.OK)
            }else{
                call.respond(HttpStatusCode.Unauthorized)
            }

        }
    }

}
