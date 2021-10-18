package com.example.data.response

data class ProfileResponse(
    val username:String,
    val profilePictureUrl:String,
    val bio:String,
    val followerCount:Int,
    val followingCount:Int,
    val postCount:Int,
    val isBuddy:Boolean,
    val isOwnProfile:String
)
