package com.example.data.repository.post

import com.example.data.models.Like
import com.example.data.models.Post
import com.example.data.models.User
import com.example.data.response.PostResponse
import com.example.data.response.PostResponsee
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.eq
import org.litote.kmongo.setValue

class PostRepositoryImpl(
    db: CoroutineDatabase
) : PostRepository {
     val posts = db.getCollection<Post>()
    private val users = db.getCollection<User>()
    private val likes = db.getCollection<Like>()

    override suspend fun createPost(post: Post): Boolean {
        return posts.insertOne(post).wasAcknowledged()
    }

    override suspend fun deletePost(postId: String) {
        posts.deleteOneById(postId)
    }

    override suspend fun getPostsByFollows(userId: String, page: Int, pageSize: Int): List<Post> {
        return posts.find(
            Post::userId eq userId
        ).skip(page * pageSize).limit(pageSize).descendingSort(Post::timestamp).toList()
    }

    override suspend fun getPostsForProfile(userId: String, page: Int, pageSize: Int): List<Post> {

        return posts.find(
            Post::userId eq userId
        ).skip(page * pageSize).limit(pageSize).descendingSort(Post::timestamp).toList()
    }


    override suspend fun getPost(postId: String): Post? {
        return posts.findOneById(postId)
    }

    override suspend fun getAllPosts(page: Int, pageSize: Int): List<Post> {

        return posts.find().skip(page * pageSize).limit(pageSize).descendingSort(Post::timestamp).toList()
    }

    override suspend fun getPostDetails(postId: String, userId: String): PostResponsee? {
        val isLiked = likes.findOne(Like::userId eq userId) != null
        val post = posts.findOneById(postId) ?: return null
        val user = users.findOneById(userId) ?: return null
        return PostResponsee(
            id = post.id,
            username = user.username,
            userId = user.id,
            imageUrl = post.imageUrl,
            profilePictureUrl = user.profileImageUrl,
            description = post.description,
            likeCount = post.likeCount,
            commentCount = post.commentCount,
            isLike = isLiked
        )
    }

    override suspend fun updateCommentCount(postId: String): Boolean {
        val post = posts.findOneById(postId) ?: return false
        posts.updateOneById(
            id = postId,
            update = setValue(Post::commentCount, (post.commentCount + 1).coerceAtLeast(0))
        )
        return true
    }
}