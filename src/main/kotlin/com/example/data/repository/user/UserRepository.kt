package com.example.data.repository.user

import com.example.data.request.UpdateProfileRequest
import com.example.data.models.User
import com.example.data.response.ProfileResponse

interface UserRepository {

    //DTO for the user collection

    suspend fun createUser(user:User)

    suspend fun getUserById(userId:String):User?

    suspend fun getUserByEmail(email:String):User?

    /*suspend fun getMyProfile(userId: String):ProfileResponse?
*/
   /* suspend fun updateUser(
        userId: String,
        profileImageUrl:String,
        bannerImageUrl: String?,
        updateProfileRequest: UpdateProfileRequest
    ):Boolean
*/
    suspend fun updateUser(
        userId: String,
        updateProfileRequest: UpdateProfileRequest
    ):Boolean

    suspend fun searchForUsers(query:String):List<User>

    suspend fun getUsers(userIds: List<String>): List<User>

}