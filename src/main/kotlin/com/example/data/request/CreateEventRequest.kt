package com.example.data.request

data class CreateEventRequest(
    val title:String,
    val place:String,
    val date:String,
    val time: String,
    val category:String,
    val subCategory:String
)
