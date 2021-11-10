package com.example.data.response

data class ProfileResponse(
    val userId:String,
    val username:String,
    val profilePictureUrl:String,
    val bio:String,
    val hobbies:List<String>,
    val followerCount:Int,
    val followingCount:Int,
    val postCount:Int,
    val bannerImageUrl:String,
    val isBuddy:Boolean,
    val isOwnProfile:Boolean
)
