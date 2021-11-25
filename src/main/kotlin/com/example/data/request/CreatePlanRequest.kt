package com.example.data.request

data class CreatePlanRequest(
    val title:String,
    val from:String,
    val to:String,
    val username:String,
    val profilePictureUrl:String,
    val date:String,
    val time: String,
    val category:String,
    val subCategory:String
)
