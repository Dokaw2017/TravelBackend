package com.example.data.request

data class FollowUpdateRequest(
    val followingUserId:String,
    val followedUserId:String,
)
