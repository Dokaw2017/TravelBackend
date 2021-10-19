package com.example.di

import com.example.Utils.Constants.DATABASE_NAME
import com.example.data.repository.follow.FollowRepository
import com.example.data.repository.follow.FollowRepositoryImpl
import com.example.data.repository.sportplan.SportPlanRepository
import com.example.data.repository.sportplan.SportPlanRepositoryImpl
import com.example.data.repository.user.UserRepository
import com.example.data.repository.user.UserRepositoryImp
import com.example.service.SportPlanService
import com.example.service.UserService
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
    single<FollowRepository>{
        FollowRepositoryImpl(get())
    }
    single<SportPlanRepository>{
        SportPlanRepositoryImpl(get())
    }

    single { UserService(get()) }
    single { SportPlanService(get()) }
}