package com.example.data.repository.comment

import com.example.data.models.Comment
import com.example.data.response.CommentResponse

interface CommentRepository {

    suspend fun createComment(comment: Comment)

    suspend fun deleteComment(commentId:String):Boolean

    suspend fun deleteCommentsFromPost(postId: String):Boolean

    suspend fun getCommentsForPost(postId:String):List<CommentResponse>

    suspend fun getComment(commentId: String): Comment?
}