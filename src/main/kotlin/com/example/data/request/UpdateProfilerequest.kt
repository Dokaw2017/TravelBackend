package com.example.data.request

data class UpdateProfileRequest (
    val username:String,
    val firstName:String,
    val lastName:String,
    val bio:String,
    val birthDay: String,
    val location:String,
    val phoneNumber:Long,
    val hobbies:List<String>,
        )