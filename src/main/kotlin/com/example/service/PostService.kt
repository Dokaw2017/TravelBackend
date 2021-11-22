package com.example.service

import com.example.Utils.Constants.DEFAULT_PAGE_SIZE
import com.example.data.models.Post
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
        //suspend fun createPost(request: CreatePostRequest, userId: String, imageUrl:String):Boolean
        return repository.createPost(
            Post(
                imageUrl = request.image,
                userId = userId,
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

    suspend fun getPost(userId:String,postId:String): PostResponsee? {
        return repository.getPost(postId,userId)
    }

    suspend fun getAllPosts(page:Int = 0,
                            pageSize:Int = DEFAULT_PAGE_SIZE):List<Post>{
        return repository.getAllPosts(page,pageSize)
    }

    suspend fun getAllPost(userId: String,page:Int = 0,
                           pageSize:Int = DEFAULT_PAGE_SIZE):List<PostResponse?>{

        val user = userRepository.getUserById(userId)
        val posts = repository.getAllPosts()

        return posts.map { post ->
            user?.let {
                PostResponse(
                    id = post.id,
                    imageUrl = post.imageUrl,
                    userId = user.id,
                    username = user.username,
                    profileImageUrl = user.profileImageUrl,
                    timestamp = post.timestamp,
                    description = post.description,
                    likeCount = post.likeCount,
                    commentCount = post.commentCount
                )
            }
        }
    }
}