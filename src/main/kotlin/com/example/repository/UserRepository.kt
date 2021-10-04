package com.example.repository

import com.example.data.models.User

interface UserRepository {

    suspend fun createUser(user:User)

    suspend fun getUserById(userId:String):User?

    suspend fun getUserByEmail(email:String):User?
}