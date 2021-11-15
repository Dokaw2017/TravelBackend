package com.example.data.repository.user

import com.example.data.request.UpdateProfileRequest
import com.example.data.models.User
import com.example.data.response.ProfileResponse
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.regex


class UserRepositoryImp(
    db:CoroutineDatabase
): UserRepository {
    private val users = db.getCollection<User>()
    override suspend fun createUser(user: User) {
       users.insertOne(user)
    }

    override suspend fun getUserById(userId: String): User? {
      return  users.findOneById(userId)

    }

    override suspend fun getUserByEmail(email: String): User? {
        return users.findOne(User::email eq email)
    }

    /*override suspend fun getMyProfile(userId: String): ProfileResponse? {
        return users.findOne(User::id eq userId)
    }*/

    override suspend fun updateUser(
        userId: String,
        profileImageUrl:String,
        bannerImageUrl:String,
        updateProfileRequest: UpdateProfileRequest
    ): Boolean {
        val user = getUserById(userId) ?: return false
        return users.updateOneById(
            id = userId,
            update = User(
                username = updateProfileRequest.username,
                password = user.password,
                profileImageUrl = profileImageUrl,
                bannerImageUrl = bannerImageUrl,
                firstName = "",
                lastname = "",
                email = user.email,
                phoneNumber = 0,
                gender = user.gender,
                location = "",
                hobbies = updateProfileRequest.hobbies,
                birthDay = "",
                buddyId = listOf("","",""),
                inviteId = listOf("","",""),
                friendsId = "",
                bio = updateProfileRequest.bio,
                followerCount = 7,
                followingCount = 4,
                postCount = 5,
                chatGroupId = listOf("","",""),
                id = userId
            )
        ).wasAcknowledged()
    }

    override suspend fun searchForUsers(query: String): List<User> {
        return users.find(User::username regex Regex("(?i).*$query.*")).toList()
    }

}