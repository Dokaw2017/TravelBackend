package com.example.repository

import com.example.data.models.User
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class UserRepositoryImp(
    db:CoroutineDatabase
):UserRepository {
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
}