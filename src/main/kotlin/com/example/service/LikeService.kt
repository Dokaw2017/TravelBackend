package com.example.service

import com.example.data.repository.like.LikeRepository

class LikeService(
    private val repository: LikeRepository,
) {
    suspend fun likePost(userId:String,postId:String):Boolean{
        return repository.likePost(userId,postId)
    }

    suspend fun unLikePost(userId:String,postId:String):Boolean{
        return repository.unLikePost(userId,postId)
    }

    suspend fun deleteLikesForPost(postId: String){
        repository.deleteLikesForPost(postId)
    }
}