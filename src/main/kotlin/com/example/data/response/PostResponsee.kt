package com.example.data.response

data class PostResponsee(
    val id:String,
    val userId:String,
    val imageUrl:String,
    val username:String,
    val profilePictureUrl:String,
    val description:String,
    val likeCount:Int = 0,
    val commentCount:Int = 0,
    val isLike:Boolean
)
