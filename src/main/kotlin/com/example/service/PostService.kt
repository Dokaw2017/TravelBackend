package com.example.service

import com.example.Utils.Constants.DEFAULT_PAGE_SIZE
import com.example.data.models.Post
import com.example.data.repository.post.PostRepository
import com.example.data.request.CreatePostRequest

class PostService(
    private val repository: PostRepository
) {
    suspend fun createPost(request: CreatePostRequest, userId: String, imageUrl:String):Boolean{
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

    suspend fun getPost(postId:String): Post? = repository.getPost(postId)

    suspend fun getAllPosts(page:Int = 0,
                            pageSize:Int = DEFAULT_PAGE_SIZE):List<Post>{
        return repository.getAllPosts(page,pageSize)
    }
}