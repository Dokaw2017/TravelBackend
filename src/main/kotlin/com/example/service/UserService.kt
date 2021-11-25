package com.example.service

import com.example.data.request.UpdateProfileRequest
import com.example.data.models.User
import com.example.data.repository.follow.FollowRepository
import com.example.data.repository.user.UserRepository
import com.example.data.request.RegistrationRequest
import com.example.data.response.ProfileResponse
import com.example.data.response.ProfileResponse2

class UserService(
    private val userRepository: UserRepository,
    private val followRepository: FollowRepository
) {

    suspend fun createUser(request: RegistrationRequest){
        userRepository.createUser(
            User(
                email = request.email,
                username = request.username,
                password = request.password,
                profileImageUrl = "",
                bannerImageUrl = "",
                firstName = "",
                lastname = "",
                phoneNumber = 123456789,
                gender = "",
                location = "",
                hobbies = listOf("","",""),
                birthDay = "",
                buddyId = listOf("","",""),
                inviteId = listOf("","",""),
                friendsId = "",
                chatGroupId = listOf("","",""),
                bio = "",
                followerCount = 5,
                followingCount = 2,
                postCount = 7
            )
        )
    }

    suspend fun getUserByEmail(email: String):User?{
        return userRepository.getUserByEmail(email)
    }

    fun isValidPassword(enteredPassword:String,actualPassword:String):Boolean{
        return enteredPassword == actualPassword
    }


    suspend fun getUserProfile(userId: String, callerUserId:String):ProfileResponse?{
        val user = userRepository.getUserById(userId) ?: return null
        return ProfileResponse(
            userId = user.id,
            username = user.username,
            bio = user.bio,
            followerCount = user.followerCount,
            followingCount = user.followingCount,
            postCount = user.postCount,
            bannerImageUrl = user.bannerImageUrl,
            profilePictureUrl = user.profileImageUrl,
            hobbies = user.hobbies,
            isOwnProfile = userId == callerUserId,
            isBuddy = if (userId != callerUserId){
                followRepository.doesUserFollow(callerUserId,userId)
            }else{
                false
            }
            )
    }

    suspend fun getMyProfile(userId: String): ProfileResponse2?{
        val user = userRepository.getUserById(userId) ?: return null

        return ProfileResponse2(
            userId = user.id,
            username = user.username,
            bio = user.bio,
            followerCount = user.followerCount,
            followingCount = user.followingCount,
            postCount = user.postCount,
            bannerImageUrl = user.bannerImageUrl,
            profilePictureUrl = user.profileImageUrl,
            hobbies = user.hobbies,
            isBuddy = false,
        )
    }

    suspend fun getUserById(userId: String):User?{
            return userRepository.getUserById(userId)
    }

    suspend fun updateUser(
        userId: String,
        updateProfileRequest: UpdateProfileRequest
    ):Boolean{
        return userRepository.updateUser(userId,updateProfileRequest)
    }
/*
    suspend fun updateUser(
        userId: String,
        profileImageUrl:String,
        bannerImageUrl:String?,
        updateProfileRequest: UpdateProfileRequest
    ):Boolean{
        return userRepository.updateUser(userId,profileImageUrl,bannerImageUrl,updateProfileRequest)
    }*/
}