package com.example.data.response

data class PostResponse(
    val imageUrl:String,
    val userId:String,
    val username:String,
    val profileImageUrl:String,
    val timestamp:Long,
    val description:String,
    val likeCount:Int = 0,
    val commentCount:Int = 0,
)
