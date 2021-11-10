package com.example.di

import com.example.Utils.Constants.DATABASE_NAME
import com.example.data.repository.comment.CommentRepository
import com.example.data.repository.comment.CommentRepositoryImpl
import com.example.data.repository.follow.FollowRepository
import com.example.data.repository.follow.FollowRepositoryImpl
import com.example.data.repository.like.LikeRepository
import com.example.data.repository.like.LikeRepositoryImpl
import com.example.data.repository.post.PostRepository
import com.example.data.repository.post.PostRepositoryImpl
import com.example.data.repository.event.EventRepository
import com.example.data.repository.event.EventRepositoryImpl
import com.example.data.repository.user.UserRepository
import com.example.data.repository.user.UserRepositoryImp
import com.example.service.*
import com.google.gson.Gson
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.koin.dsl.module

val mainModule = module() {
    single {
        val client = KMongo.createClient(System.getenv("MONGO_URI") ?: "").coroutine
        client.getDatabase(DATABASE_NAME)
    }
    single<UserRepository>{
        UserRepositoryImp(get())
    }
    single<EventRepository>{
        EventRepositoryImpl(get())
    }
    single<FollowRepository>{
        FollowRepositoryImpl(get())
    }
    single<LikeRepository>{
        LikeRepositoryImpl(get())
    }
    single<PostRepository>{
        PostRepositoryImpl(get())
    }
    single<CommentRepository>{
        CommentRepositoryImpl(get())
    }
    single { UserService(get(),get()) }
    single { EventService(get(),get()) }
    single { FollowService(get()) }
    single { PostService(get()) }
    single { LikeService(get()) }
    single { CommentService(get()) }

    single { Gson() }
}