package com.example.service

import com.example.Utils.Constants.MAX_COMMENT_LENGTH
import com.example.data.models.Comment
import com.example.data.repository.comment.CommentRepository
import com.example.data.request.CreateCommentRequest

class CommentService(
    private val repository: CommentRepository
) {
    suspend fun createComment(createCommentRequest: CreateCommentRequest, userId:String):ValidationEvent{
        createCommentRequest.apply {
            if (comment.isBlank() || postId.isBlank()){
                return ValidationEvent.ErrorFieldEmpty
            }
            if (comment.length > MAX_COMMENT_LENGTH){
                return ValidationEvent.CommentTooLong
            }
        }

        repository.createComment(
            Comment(
                comment = createCommentRequest.comment,
                userId = userId,
                postId = createCommentRequest.postId,
                timestamp = System.currentTimeMillis()
            )
        )
        return ValidationEvent.Success
    }
    suspend fun deleteCommentsForPost(postId: String){
        repository.deleteCommentsFromPost(postId)
    }

    suspend fun deleteComment(commentId:String):Boolean{
        return repository.deleteComment(commentId)
    }

    suspend fun getCommentsForPost(postId:String):List<Comment>{
        return repository.getCommentsForPost(postId)
    }

    suspend fun getCommentById(commentId: String): Comment?{
        return repository.getComment(commentId)
    }

    sealed class ValidationEvent{
        object ErrorFieldEmpty : ValidationEvent()
        object CommentTooLong : ValidationEvent()
        object Success:ValidationEvent()
    }
}