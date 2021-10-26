package com.example.data.repository.user

import com.example.data.request.UpdateProfileRequest
import com.example.data.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import java.util.*

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
                firstName = updateProfileRequest.firstName,
                lastname = updateProfileRequest.lastName,
                email = user.email,
                phoneNumber = updateProfileRequest.phoneNumber,
                gender = user.gender,
                location = updateProfileRequest.location,
                hobbies = updateProfileRequest.hobbies,
                birthDay = updateProfileRequest.birthDay,
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

}