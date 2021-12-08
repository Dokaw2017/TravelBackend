package com.example.data.repository.comment

import com.example.data.models.Comment
import com.example.data.models.Like
import com.example.data.models.User
import com.example.data.response.CommentResponse
import org.litote.kmongo.and
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq

class CommentRepositoryImpl(
    db: CoroutineDatabase
):CommentRepository {

    //implementation for the comment dto

   private val comments = db.getCollection<Comment>()
    private  val likes = db.getCollection<Like>()

    override suspend fun createComment(comment: Comment) {
        comments.insertOne(comment)
    }

    override suspend fun deleteComment(commentId: String): Boolean {
        val deleteCount = comments.deleteOneById(commentId).deletedCount
        return deleteCount > 0
    }

    override suspend fun deleteCommentsFromPost(postId: String): Boolean {
        return comments.deleteMany(Comment::postId eq postId).wasAcknowledged()
    }

    override suspend fun getCommentsForPost(postId: String,ownUserId:String): List<CommentResponse> {

        return comments.find(Comment::postId eq  postId).toList().map{comment ->
            println("USER ID : ${comment.userId}")
            println("USER ID : ${comment.comment}")
            println("COMMENT ID : ${comment.id}")
            val isLiked = likes.findOne(
                and(
                    Like::userId eq ownUserId,
                    Like::parentId eq comment.id
                )
            ) != null
                CommentResponse(
                    id = comment.id,
                    username = comment.username,
                    profilePictureUrl = comment.profileImageUrl,
                    timestamp = comment.timestamp,
                    isLiked = isLiked,
                    comment = comment.comment,
                    likeCount = comment.likeCount
                )
        }
    }

    override suspend fun getComment(commentId: String): Comment? {
        return comments.findOneById(commentId)
    }
}