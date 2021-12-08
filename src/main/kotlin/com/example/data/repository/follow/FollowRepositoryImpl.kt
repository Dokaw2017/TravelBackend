package com.example.data.repository.follow

import com.example.data.models.Following
import com.example.data.models.User
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class FollowRepositoryImpl(
    db: CoroutineDatabase
): FollowRepository {

    //implementation for the follow dto

    private val following = db.getCollection<Following>()
    private val users = db.getCollection<User>()

    override suspend fun followUser(followingUserId: String, followedUserId: String): Boolean {
        val doesFollowingUserExist = users.findOneById(followingUserId) != null
        val doesFollowedUserExist = users.findOneById(followedUserId) != null

        if (!doesFollowingUserExist || !doesFollowedUserExist){
            return false
        }
        following.insertOne(
            Following(followedUserId,followingUserId)
        )
        return true
    }

    override suspend fun unFollowUser(followingUserId: String, followedUserId: String): Boolean {

        val deletedResult = following.deleteOne(
            and(
                Following::followingUserId eq followedUserId,
                Following::followedUserId eq followedUserId
            )
        )
        return deletedResult.deletedCount > 0
    }

    override suspend fun getFollowsByUser(userId: String): List<Following> {
        return following.find(Following::followingUserId eq userId).toList()
    }

    override suspend fun doesUserFollow(followingUserId: String, followedUserId: String): Boolean {
        return following.findOne(
            and(
                Following::followingUserId eq followingUserId,
                Following::followedUserId eq followedUserId
            )
        ) != null
    }

}