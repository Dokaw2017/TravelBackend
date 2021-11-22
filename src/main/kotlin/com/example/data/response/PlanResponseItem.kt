package com.example.data.response

data class PlanResponseItem(
    val userId:String,
    val username:String,
    val profileImageUrl:String,
    val from:String,
    val to:String,
    val date:String,
    val time:String,
    val isBuddy:Boolean
)
