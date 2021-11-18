package com.example.data.repository.like

import com.example.data.models.Comment
import com.example.data.models.Like
import com.example.data.models.Post
import com.example.data.models.User
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class LikeRepositoryImpl(
    db: CoroutineDatabase
):LikeRepository {
    private val likes = db.getCollection<Like>()
    private val users = db.getCollection<User>()


    override suspend fun likePost(userId: String, postId: String): Boolean {
        val doesUserExist = users.findOneById(userId) != null

        return if (doesUserExist){
            likes.insertOne(Like(userId,postId,System.currentTimeMillis()))
            true
        }else{
            false
        }
    }

    override suspend fun unLikePost(userId: String, postId: String): Boolean {
        val doesUserExist = users.findOneById(userId) != null

        return if (doesUserExist){
            likes.deleteOne(
                and(
                    Like::userId eq userId,
                    Like::postId eq postId
                )
            )
            true
        }else{
            false
        }
    }

    override suspend fun deleteLikesForPost(postId: String) {
        likes.deleteMany(Like::postId eq postId)
    }
}