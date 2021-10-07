package com.example.service

import com.example.UpdateProfileRequest
import com.example.data.models.User
import com.example.data.repository.user.UserRepository
import com.example.data.request.RegistrationRequest
import java.util.*

class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun createUser(request: RegistrationRequest){
        userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                profileImageUrl = "",
                firstName = "",
                lastname = "",
                phoneNumber = 123456789,
                gender = "",
                location = listOf("","",""),
                hobbies = listOf("","",""),
                birthDay = Date(),
                buddyId = listOf("","",""),
                inviteId = listOf("","",""),
                friendsId = "",
                chatGroupId = listOf("","",""),
            )
        )
    }

    suspend fun getUserByEmail(email: String):User?{
        return userRepository.getUserByEmail(email)
    }

    fun isValidPassword(enteredPassword:String,actualPassword:String):Boolean{
        return enteredPassword == actualPassword
    }

    suspend fun getUserById(userId: String):User?{
            return userRepository.getUserById(userId)
    }

    suspend fun updateUser(
        userId: String,
        profileImageUrl:String,
        updateProfileRequest: UpdateProfileRequest
    ):Boolean{
        return userRepository.updateUser(userId,profileImageUrl,updateProfileRequest)
    }
}