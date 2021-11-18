package com.example.service

import com.example.Utils.Constants.MAX_COMMENT_LENGTH
import com.example.data.models.Comment
import com.example.data.repository.comment.CommentRepository
import com.example.data.repository.user.UserRepository
import com.example.data.request.CreateCommentRequest
import com.example.data.response.CommentResponse

class CommentService(
    private val commentRepository: CommentRepository,
    private val userRepository: UserRepository
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

        val user = userRepository.getUserById(userId) ?: return ValidationEvent.UserNotFound

        commentRepository.createComment(
            Comment(
                username = user.username,
                profileImageUrl = user.profileImageUrl,
                likeCount = 0,
                comment = createCommentRequest.comment,
                userId = userId,
                postId = createCommentRequest.postId,
                timestamp = System.currentTimeMillis()
            )
        )
        return ValidationEvent.Success
    }
    suspend fun deleteCommentsForPost(postId: String){
        commentRepository.deleteCommentsFromPost(postId)
    }

    suspend fun deleteComment(commentId:String):Boolean{
        return commentRepository.deleteComment(commentId)
    }

    suspend fun getCommentsForPost(postId:String):List<CommentResponse>{
        return commentRepository.getCommentsForPost(postId)
    }

    suspend fun getCommentById(commentId: String): Comment?{
        return commentRepository.getComment(commentId)
    }

    sealed class ValidationEvent{
        object ErrorFieldEmpty : ValidationEvent()
        object CommentTooLong : ValidationEvent()
        object UserNotFound:ValidationEvent()
        object Success:ValidationEvent()
    }
}