package com.example.data.request

data class UpdateProfileRequest (
    val username:String,
    val profileImageUrl:String,
    val bannerImageUrl:String? = "",
    val bio:String,
    val hobbies:List<String>,
        )