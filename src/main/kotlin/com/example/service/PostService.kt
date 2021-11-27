package com.example.service

import com.example.Utils.Constants.DEFAULT_PAGE_SIZE
import com.example.data.models.Post
import com.example.data.models.User
import com.example.data.repository.post.PostRepository
import com.example.data.repository.user.UserRepository
import com.example.data.request.CreatePostRequest
import com.example.data.response.PostResponse
import com.example.data.response.PostResponsee

class PostService(
    private val repository: PostRepository,
    private val userRepository: UserRepository
) {
    suspend fun createPost(request: CreatePostRequest, userId: String):Boolean{
        val user = userRepository.getUserById(userId) ?: return false
        //suspend fun createPost(request: CreatePostRequest, userId: String, imageUrl:String):Boolean
        return repository.createPost(
            Post(
                imageUrl = request.image,
                userId = userId,
                username = user.username,
                profilePictureUrl = user.profileImageUrl,
                timestamp = System.currentTimeMillis(),
                description = request.description
            )
        )
    }

    suspend fun getPostsForFollows(
        userId:String,
        page:Int = 0,
        pageSize:Int = DEFAULT_PAGE_SIZE
    ):List<Post>{
        return repository.getPostsByFollows(userId,page,pageSize)
    }

    suspend fun deletePost(postId: String){
        repository.deletePost(postId)
    }

   suspend fun getPostsForProfile(
        userId:String,
        page:Int = 0,
        pageSize:Int = DEFAULT_PAGE_SIZE
    ):List<Post>{
        return repository.getPostsForProfile(userId,page,pageSize)
    }

    suspend fun getPost(postId:String): Post? {
        return repository.getPost(postId)
    }

    suspend fun getAllPosts(page:Int = 0,
                            pageSize:Int = DEFAULT_PAGE_SIZE,isImage: Boolean):List<Post>{
        return repository.getAllPosts(page,pageSize, isImage)
    }

   /* suspend fun getAllPost(userId: String,page:Int = 0,
                           pageSize:Int = DEFAULT_PAGE_SIZE,isImage:Boolean):List<PostResponse?>{

        val user = userRepository.getUserById(userId)
        val posts = repository.getAllPosts()


        return posts.map { post ->

            user?.let {
                PostResponse(
                    id = post.id,
                    imageUrl = post.imageUrl,
                    userId = post.userId,
                    username = user.username,
                    profileImageUrl = user.profileImageUrl,
                    timestamp = post.timestamp,
                    description = post.description,
                    likeCount = post.likeCount,
                    commentCount = post.commentCount
                )
            }
        }
    }*/

    suspend fun getPostDetails(ownUserId:String,postId:String):PostResponsee?{
        return repository.getPostDetails(ownUserId,postId)
    }

    sealed class ValidationEvent{
        object ErrorFieldEmpty : ValidationEvent()
        object CommentTooLong : ValidationEvent()
        object UserNotFound:ValidationEvent()
        object Success:ValidationEvent()
    }
}