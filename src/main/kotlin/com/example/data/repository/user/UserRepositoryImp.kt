package com.example.data.repository.user

import com.example.UpdateProfileRequest
import com.example.data.models.User
import com.example.data.repository.user.UserRepository
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
        updateProfileRequest: UpdateProfileRequest
    ): Boolean {
        val user = getUserById(userId) ?: return false
        return users.updateOneById(
            id = userId,
            update = User(
                username = updateProfileRequest.username,
                password = user.password,
                profileImageUrl = profileImageUrl,
                firstName = user.firstName,
                lastname = user.lastname,
                email = user.email,
                phoneNumber = user.phoneNumber,
                gender = user.gender,
                location = listOf("","",""),
                hobbies = listOf("","",""),
                birthDay = Date(),
                buddyId = listOf("","",""),
                inviteId = listOf("","",""),
                friendsId = "",
                chatGroupId = listOf("","",""),
                id = userId
            )
        ).wasAcknowledged()
    }

}