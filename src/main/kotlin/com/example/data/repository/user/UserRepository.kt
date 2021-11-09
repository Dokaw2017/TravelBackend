package com.example.data.repository.user

import com.example.data.request.UpdateProfileRequest
import com.example.data.models.User

interface UserRepository {

    suspend fun createUser(user:User)

    suspend fun getUserById(userId:String):User?

    suspend fun getUserByEmail(email:String):User?

    suspend fun updateUser(
        userId: String,
        profileImageUrl:String,
        bannerImageUrl:String,
        updateProfileRequest: UpdateProfileRequest
    ):Boolean

    suspend fun searchForUsers(query:String):List<User>

}