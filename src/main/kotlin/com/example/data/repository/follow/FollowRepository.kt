package com.example.data.repository.follow

import com.example.data.models.Following

interface FollowRepository {

    suspend fun followUser(
        followingUserId:String,
        followedUserId:String
    ):Boolean

    suspend fun unFollowUser(
        followingUserId: String,
        followedUserId: String
    ):Boolean
}