package com.example.service

import com.example.data.repository.follow.FollowRepository
import com.example.data.request.FollowRequest

class FollowService(
    private val followRepository: FollowRepository
) {
    suspend fun followUserIfExists(request:FollowRequest,followingUserId:String):Boolean{
        return  followRepository.followUser(
            followingUserId,
            request.followedUserId
        )
    }

    /*suspend fun unFollowUserIfExists(request: FollowRequest, followingUserId:String):Boolean{
        return  followRepository.unFollowUser(
            followingUserId,
            request.followedUserId
        )

    }*/

    suspend fun unfollowUserIfExists(followedUserId: String, followingUserId: String): Boolean {
        return followRepository.unFollowUser(
            followingUserId,
            followedUserId
        )
    }
}