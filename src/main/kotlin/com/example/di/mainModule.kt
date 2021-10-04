package com.example.di

import com.example.Utils.Constants.DATABASE_NAME
import com.example.repository.UserRepository
import com.example.repository.UserRepositoryImp
import org.koin.core.module.Module
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
}