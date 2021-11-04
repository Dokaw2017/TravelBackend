package com.example.data.models

import io.ktor.auth.*
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class User(
    val firstName:String,
    val lastname:String,
    val email:String,
    val username:String,
    val password:String,
    val phoneNumber:Long,
    val gender:String,
    val location:String,
    val hobbies:List<String>,
    val birthDay: String,
    val bio:String,
    val followerCount:Int,
    val followingCount:Int,
    val postCount:Int,
    val buddyId:List<String>,
    val inviteId:List<String>,
    val friendsId:String,
    val bannerImageUrl:String,
    val profileImageUrl:String,
    val chatGroupId:List<String>,
    @BsonId
    val id:String = ObjectId().toString()
):Principal
