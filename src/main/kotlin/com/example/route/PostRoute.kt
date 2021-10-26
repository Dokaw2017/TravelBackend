package com.example.route

import com.example.Utils.Constants
import com.example.Utils.Constants.DEFAULT_PAGE_SIZE
import com.example.Utils.Constants.POST_PICTURE_PATH
import com.example.Utils.Constants.PROFILE_PICTURE_PATH
import com.example.Utils.QueryParams
import com.example.Utils.save
import com.example.data.request.CreatePostRequest
import com.example.data.request.DeletePostRequest
import com.example.data.request.UpdateProfileRequest
import com.example.data.response.ApiResponse
import com.example.service.LikeService
import com.example.service.PostService
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
import kotlin.jvm.internal.Intrinsics

fun Route.cratePostRoute(
    postService: PostService
) {
    val gson: Gson by inject()
    authenticate {
        post("/api/post/create") {
            val multipart = call.receiveMultipart()
            var createPostRequest: CreatePostRequest? = null
            var fileName: String? = null
            multipart.forEachPart { partData ->
                when (partData) {
                    is PartData.FormItem -> {
                        if (partData.name == "post_data") {
                            createPostRequest = gson.fromJson(
                                partData.value,
                                CreatePostRequest::class.java
                            )

                        }
                    }
                    is PartData.FileItem -> {

                        fileName = partData.save(POST_PICTURE_PATH)

                    }
                    is PartData.BinaryItem -> Unit
                }
            }
            val postPictureUrl = "${Constants.BASE_URL}post_pictures/$fileName"
            //val profilePictureUrl = "${BASE_URL}src/main/$PROFILE_PICTURE_PATH$fileName"

            createPostRequest?.let { request ->

                val createPostAcknowledged = postService.createPost(
                    request = request,
                    userId = call.userId,
                    imageUrl = postPictureUrl
                )

                if (createPostAcknowledged) {
                    call.respond(HttpStatusCode.OK, ApiResponse<Unit>(true, ""))

                } else {
                    File("${Constants.POST_PICTURE_PATH}/$fileName").delete()
                    call.respond(HttpStatusCode.InternalServerError)

                }
            } ?: kotlin.run {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
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