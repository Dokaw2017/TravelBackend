package com.example.data.repository.like

interface LikeRepository {
    suspend fun likePost(userId:String,postId:String):Boolean

    suspend fun unLikePost(userId: String,postId:String):Boolean

    suspend fun deleteLikesForPost(postId: String)
}