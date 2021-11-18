package com.example.data.request

data class CreatePlanRequest(
    val title:String,
    val place:String,
    val date:String,
    val time: String,
    val category:String,
    val subCategory:String
)
