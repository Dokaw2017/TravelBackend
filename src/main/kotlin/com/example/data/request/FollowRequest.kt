package com.example.data.request

data class FollowRequest(
    val followingUserId:String,
    val followedUserId:String,
)
